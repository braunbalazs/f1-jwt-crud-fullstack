import {
  Avatar,
  Button,
  FormControl,
  Grid,
  IconButton,
  Input,
  InputAdornment,
  InputLabel,
  Paper,
  TextField,
  Typography,
} from "@mui/material";
import LockOutlinedIcon from "@mui/icons-material/LockOutlined";
import { Visibility, VisibilityOff } from "@mui/icons-material";
import React, { useEffect, useRef, useState } from "react";
import { useAuth } from "../auth/AuthProvider";
import { useLocation, useNavigate } from "react-router-dom";
import axios from "axios";

export default function Login() {
  const [loginInProgress, setLoginInProgress] = useState(false);
  const [showPassword, setShowPassword] = useState(false);

  const handleClickShowPassword = () => setShowPassword((show) => !show);

  const handleMouseDownPassword = (
    event: React.MouseEvent<HTMLButtonElement>
  ) => {
    event.preventDefault();
  };
  const paperStyle = {
    padding: 32,
    // height: "60vh",
    width: 280,
    margin: "40px auto",
  };

  const avatarStyle = {
    backgroundColor: "#0d8c01",
  };

  const location = useLocation();
  const target = location.state?.from || "/";

  const { token, setToken } = useAuth();
  const navigate = useNavigate();
  const [formData, setFormData] = useState({
    username: "",
    password: "",
  });

  function handleTextFieldChange(event: React.ChangeEvent<HTMLInputElement>) {
    const { name, value } = event.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  }

  async function handleLogin() {
    try {
      const response = await axios.post(
        "http://localhost:8080/login",
        formData
      );
      setToken(response.data.token);
    } catch (error) {
      setLoginInProgress(false);
    }
  }

  useEffect(() => {
    if (token) {
      navigate(target);
      setLoginInProgress(false);
    }
  }, [token, navigate, target]);

  return (
    <Grid>
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
          <Avatar style={avatarStyle}>
            <LockOutlinedIcon />
          </Avatar>
          <Typography sx={{ fontSize: "1.25rem", fontWeight: "700" }}>
            Log in
          </Typography>
        </Grid>
        <Grid container direction="column" alignItems="center" gap="20px">
          <TextField
            name="username"
            value={formData.username}
            onChange={handleTextFieldChange}
            label="Username"
            placeholder="Enter your username"
            fullWidth
            size="small"
            variant="standard"
          />
          <FormControl variant="standard" fullWidth>
            <InputLabel htmlFor="standard-adornment-password">
              Password
            </InputLabel>
            <Input
              name="password"
              value={formData.password}
              onChange={handleTextFieldChange}
              placeholder="Enter your password"
              id="standard-adornment-password"
              type={showPassword ? "text" : "password"}
              endAdornment={
                <InputAdornment position="end">
                  <IconButton
                    aria-label="toggle password visibility"
                    onClick={handleClickShowPassword}
                    onMouseDown={handleMouseDownPassword}
                  >
                    {showPassword ? <VisibilityOff /> : <Visibility />}
                  </IconButton>
                </InputAdornment>
              }
            />
          </FormControl>
          <Button
            onClick={handleLogin}
            disabled={loginInProgress}
            type="submit"
            color="primary"
            variant="contained"
            fullWidth
          >
            Log in
          </Button>
        </Grid>
      </Paper>
      <Typography variant="h3">{location.search}</Typography>
    </Grid>
  );
}
