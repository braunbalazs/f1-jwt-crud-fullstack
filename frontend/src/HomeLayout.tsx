import {
  AppBar,
  Box,
  Button,
  Dialog,
  DialogActions,
  DialogTitle,
  IconButton,
  Toolbar,
  Typography,
} from "@mui/material";
import Logo from "./assets/Logo";
import { Link, NavLink, Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "./auth/AuthProvider";
import { useEffect, useState } from "react";
import axios from "axios";

export default function HomeLayout() {
  const { token, setToken } = useAuth();
  const navigate = useNavigate();
  const [logoutDialogOpen, setLogoutDialogOpen] = useState(false);
  const [logoutInProgress, setLogoutInProgress] = useState(false);

  function handleLogout() {
    async function logout() {
      try {
        setLogoutInProgress(true);
        await axios.post("http://localhost:8080/doLogout", {});
        setToken(null);
      } catch (error) {
        console.log("Error during logout: " + error);
        setLogoutInProgress(false);
      }
    }
    logout();
  }

  useEffect(() => {
    if (logoutInProgress) {
      setLogoutInProgress(false);
      setLogoutDialogOpen(false);
      navigate("/teams");
    }
  }, [logoutInProgress]);

  return (
    <>
      <AppBar position="static">
        <Toolbar>
          <Box width="100%" display="flex" alignItems="center">
            <Box display="flex" alignItems="center">
              <IconButton>
                <Logo />
              </IconButton>
              <Typography variant="h5" component="div">
                F1 Teams
              </Typography>
            </Box>
            <Box marginInline="auto">
              <Button component={NavLink} to="/teams" color="inherit">
                Teams
              </Button>
              {token == null ? (
                <Button component={NavLink} to="/login" color="inherit">
                  Log in
                </Button>
              ) : (
                <Button
                  color="inherit"
                  onClick={() => setLogoutDialogOpen(true)}
                >
                  Log out
                </Button>
              )}

              {/* <Button
                component={NavLink}
                to={token == null ? "/login" : "/logout"}
                color="inherit"
              >
                Log {token == null ? "in" : "out"}
              </Button> */}
            </Box>
          </Box>
        </Toolbar>
      </AppBar>
      <Dialog
        open={logoutDialogOpen}
        onClose={() => setLogoutDialogOpen(false)}
      >
        <DialogTitle>
          <Typography fontWeight="bold" fontSize="24px">
            Are you sure you want to log out?
          </Typography>
        </DialogTitle>
        {/* <DialogContent>
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
        </DialogContent> */}
        <DialogActions sx={{ mt: 2 }}>
          <Button variant="contained" onClick={handleLogout}>
            Log out
          </Button>
          <Button onClick={() => setLogoutDialogOpen(false)}>Cancel</Button>
        </DialogActions>
      </Dialog>
      <Outlet />
    </>
  );
}
