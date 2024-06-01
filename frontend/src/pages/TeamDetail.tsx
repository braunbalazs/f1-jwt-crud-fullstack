import {
  Avatar,
  Button,
  CircularProgress,
  FormControlLabel,
  Grid,
  Paper,
  Stack,
  Switch,
  TextField,
  Typography,
} from "@mui/material";
import { useLocation, useNavigate, useParams } from "react-router-dom";
import BuildCircleOutlinedIcon from "@mui/icons-material/BuildCircleOutlined";
import { Controller, useForm } from "react-hook-form";
import { useEffect, useState } from "react";
import axios from "axios";
import type { Team } from "./Teams";
import { Token } from "@mui/icons-material";

type FormValues = {
  name: string;
  yearEstablished: number;
  championshipsWon: number;
  regFeePaid: boolean;
};

export default function TeamDetail() {
  const { teamId } = useParams();
  const [, setTeam] = useState<Team>();
  const navigate = useNavigate();
  const location = useLocation();
  const paperStyle = {
    padding: 32,
    width: 280,
    margin: "40px auto",
  };

  const form = useForm<FormValues>({
    defaultValues: {
      name: "",
      yearEstablished: new Date().getFullYear(),
      championshipsWon: 0,
      regFeePaid: false,
    },
  });
  console.log(form);
  const {
    handleSubmit,
    reset,
    control,
    formState: { errors },
  } = form;

  console.log("TeamId: " + teamId);

  function onSubmit(data: FormValues) {
    async function putTeam() {
      try {
        await axios.put(`http://localhost:8080/team/${teamId}`, data);
        navigate("/teams");
      } catch (error) {
        console.log(error);
      }
    }
    async function postTeam() {
      try {
        await axios.post(`http://localhost:8080/team/new`, data);
        navigate("/teams");
      } catch (error) {
        console.log(error);
      }
    }
    if (teamId) {
      putTeam();
    } else {
      postTeam();
    }
  }

  useEffect(() => {
    async function fetchTeam() {
      try {
        const data = (await axios.get(`http://localhost:8080/team/${teamId}`))
          .data;
        console.log(data);
        setTeam(data);
        reset({
          name: data.name,
          yearEstablished: data.yearEstablished,
          championshipsWon: data.championshipsWon,
          regFeePaid: data.regFeePaid,
        });
      } catch (error) {
        console.log(error);
        navigate(`/login?path=${location.pathname}`, {
          state: { from: location.pathname },
        });
      }
    }
    if (teamId) {
      fetchTeam();
    }
  }, [teamId]);

  // if (!team) {
  //   return <CircularProgress />;
  // }

  return (
    <Stack>
      <Paper elevation={10} style={paperStyle}>
        <Grid
          marginBlock="20px"
          gap="20px"
          container
          //   spacing={0}
          direction="column"
          alignItems="center"
          //   justifyContent="center"
        >
          <Avatar sx={{ bgcolor: "warning.main" }}>
            <BuildCircleOutlinedIcon />
          </Avatar>
          <Typography sx={{ fontSize: "1.25rem", fontWeight: "700" }}>
            {teamId ? "Edit" : "Create"} Team
          </Typography>
        </Grid>
        <Grid container direction="column" alignItems="center" gap="20px">
          <form onSubmit={handleSubmit(onSubmit)}>
            <Grid container rowGap={4}>
              <Controller
                name="name"
                control={control}
                rules={{
                  required: "Team name is required",
                  validate: {
                    length: (value) =>
                      (value.length >= 3 && value.length <= 100) ||
                      "Team name must be between 3 and 100 characters long",
                  },
                }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    label="Team name"
                    fullWidth
                    size="small"
                    variant="standard"
                    error={!!errors.name}
                    helperText={errors.name?.message}
                  />
                )}
              />
              <Controller
                name="yearEstablished"
                control={control}
                rules={{
                  required: "'Established in' is required",
                  max: {
                    value: new Date().getFullYear(),
                    message: `'Established in cannot be later than ${new Date().getFullYear()}`,
                  },
                }}
                render={({ field }) => (
                  <TextField
                    {...field}
                    type="number"
                    label="Established in"
                    placeholder="Established in"
                    fullWidth
                    inputProps={{ max: 2024 }}
                    size="small"
                    variant="standard"
                    error={!!errors.yearEstablished}
                    helperText={errors.yearEstablished?.message}
                  />
                )}
              />
              <Controller
                name="championshipsWon"
                control={control}
                render={({ field }) => (
                  <TextField
                    {...field}
                    type="number"
                    inputProps={{ min: 0 }}
                    label="Number of championships won"
                    fullWidth
                    size="small"
                    variant="standard"
                    error={!!errors.championshipsWon}
                    helperText={errors.championshipsWon?.message}
                  />
                )}
              />
              <Controller
                name="regFeePaid"
                control={control}
                render={({ field }) => (
                  <FormControlLabel
                    control={<Switch {...field} checked={field.value} />}
                    label="Registration Fee Paid"
                  />
                )}
              />
              <Button
                type="submit"
                color="primary"
                variant="contained"
                fullWidth
              >
                Save
              </Button>
            </Grid>
          </form>
        </Grid>
      </Paper>
      <Typography variant="h3">{location.search}</Typography>
    </Stack>
  );
}
