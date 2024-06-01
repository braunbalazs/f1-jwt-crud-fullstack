import {
  Avatar,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogContent,
  DialogContentText,
  DialogTitle,
  Grid,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TablePagination,
  TableRow,
  Typography,
} from "@mui/material";
import axios from "axios";
import { useEffect, useState } from "react";
import CreateOutlinedIcon from "@mui/icons-material/CreateOutlined";
import ReportProblemOutlinedIcon from "@mui/icons-material/ReportProblemOutlined";
import DeleteForeverOutlinedIcon from "@mui/icons-material/DeleteForeverOutlined";
import BuildOutlinedIcon from "@mui/icons-material/BuildOutlined";
import { useLocation, useNavigate } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";

export type Team = {
  id: number;
  name: string;
  yearEstablished: number;
  championshipsWon: number;
  regFeePaid: boolean;
};

const rowsPerPageOptions = [10, 20, 50];

export default function Teams() {
  const [teams, setTeams] = useState<Team[]>([]);
  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(rowsPerPageOptions[0]);
  const navigate = useNavigate();
  const location = useLocation();
  const [dialogOpen, setDialogOpen] = useState(false);
  const [idToDelete, setIdToDelete] = useState<number>();
  const { token } = useAuth();

  async function fetchTeams() {
    try {
      const data = (await axios.get("http://localhost:8080/team")).data;
      setTeams(data);
    } catch (error) {
      console.log(error);
    }
  }
  useEffect(() => {
    fetchTeams();
  }, []);

  function handleEditTeam(teamId: number) {
    navigate(`${location.pathname}/${teamId}`);
  }

  function toggleDeleteDialog(teamId: number) {
    if (!token) {
      navigate(`/login?path=${location.pathname}`, {
        state: { from: location.pathname },
      });
    }
    setIdToDelete(teamId);
  }

  function handleDeleteTeam() {
    async function deleteTeam() {
      try {
        await axios.delete(`http://localhost:8080/team/${idToDelete}`);
        setIdToDelete(undefined);
        fetchTeams();
      } catch (error) {
        navigate(`/login?path=${location.pathname}`, {
          state: { from: location.pathname },
        });
      }
    }
    deleteTeam();
  }

  return (
    <>
      <TableContainer
        sx={{ mt: 10, maxWidth: { xs: "96%", md: "75%" }, mx: "auto" }}
      >
        <Table
          size="small"
          sx={{
            "& .MuiTableCell-root": {
              whiteSpace: "nowrap",
              overflow: "hidden",
              textOverflow: "ellipsis",
              "&:first-child": {
                maxWidth: "150px",
              },
              "&:not(:first-child)": {
                maxWidth: "50px",
              },
            },
          }}
        >
          <TableHead>
            <TableRow
              sx={{
                "& .MuiTypography-root": {
                  fontWeight: "bold",
                },
              }}
            >
              <TableCell>
                <Typography title="Name" noWrap>
                  Name
                </Typography>
              </TableCell>
              <TableCell>
                <Typography title="Established" noWrap>
                  Established
                </Typography>
              </TableCell>
              <TableCell>
                <Typography title="Championships" noWrap>
                  Championships won
                </Typography>
              </TableCell>
              <TableCell>
                <Typography title="Registration fee" noWrap>
                  Registration fee
                </Typography>
              </TableCell>
              <TableCell>
                <Typography title="Registration fee" noWrap>
                  Actions
                </Typography>
              </TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {teams
              .slice(page * rowsPerPage, page * rowsPerPage + rowsPerPage)
              .map((team, i) => (
                <TableRow sx={{ bgcolor: i % 2 === 0 ? "#CAF4FF" : "fff" }}>
                  <TableCell>
                    <Typography title={team.name} noWrap>
                      {team.name}
                    </Typography>
                  </TableCell>
                  <TableCell>{team.yearEstablished}</TableCell>
                  <TableCell>{team.championshipsWon}</TableCell>
                  <TableCell>{team.regFeePaid ? "paid" : "unpaid"}</TableCell>
                  <TableCell>
                    <Grid
                      sx={{
                        "& .MuiButton-root": { p: 0, minWidth: 32 },
                      }}
                    >
                      <Button onClick={() => handleEditTeam(team.id)}>
                        <BuildOutlinedIcon fontSize="small" />
                      </Button>
                      <Button onClick={() => toggleDeleteDialog(team.id)}>
                        <DeleteForeverOutlinedIcon
                          sx={{ color: "error.main" }}
                        />
                      </Button>
                    </Grid>
                  </TableCell>
                </TableRow>
              ))}
          </TableBody>
        </Table>
        <TablePagination
          component="div"
          rowsPerPageOptions={rowsPerPageOptions}
          count={teams.length}
          rowsPerPage={rowsPerPage}
          page={page}
          onPageChange={(event, newPageNum) => setPage(newPageNum)}
          onRowsPerPageChange={(event) =>
            setRowsPerPage(Number.parseInt(event.target.value))
          }
        />
      </TableContainer>
      <Dialog
        open={idToDelete != undefined}
        onClose={() => setIdToDelete(undefined)}
      >
        <DialogTitle>
          <Typography fontWeight="bold" fontSize="24px">
            Delete Team
          </Typography>
        </DialogTitle>
        <DialogContent>
          <Box>
            <ReportProblemOutlinedIcon
              fontSize="large"
              sx={{
                mx: "auto",
                my: 2,
                width: "100%",
                textAlign: "center",
                color: "error.main",
                fontSize: 64,
              }}
            />
          </Box>
          <DialogContentText>
            Are you sure you want to permanently delete{" "}
            <Typography fontWeight="bold">
              {teams.find((team) => team.id === idToDelete)?.name}
            </Typography>
          </DialogContentText>
        </DialogContent>
        <DialogActions sx={{ mt: 2 }}>
          <Button variant="contained" onClick={handleDeleteTeam}>
            Delete
          </Button>
          <Button onClick={() => setIdToDelete(undefined)}>Cancel</Button>
        </DialogActions>
      </Dialog>
    </>
  );
}
