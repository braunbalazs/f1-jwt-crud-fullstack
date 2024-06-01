import {
  AppBar,
  Box,
  Button,
  IconButton,
  Toolbar,
  Typography,
} from "@mui/material";
import Logo from "./assets/Logo";
import { Link, NavLink, Outlet } from "react-router-dom";
import { useAuth } from "./auth/AuthProvider";

export default function HomeLayout() {
  const { token } = useAuth();

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

              <Button
                component={NavLink}
                to={token == null ? "/login" : "/logout"}
                color="inherit"
              >
                Log {token == null ? "in" : "out"}
              </Button>
            </Box>
          </Box>
        </Toolbar>
      </AppBar>
      <Outlet />
    </>
  );
}
