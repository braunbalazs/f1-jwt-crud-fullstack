import React from "react";
import ReactDOM from "react-dom/client";
import App from "./App.tsx";
import "./index.css";
import { RouterProvider } from "react-router-dom";
import { router } from "./router.tsx";
import AuthProvider from "./auth/AuthProvider.tsx";
import { ThemeProvider, createTheme } from "@mui/material";

const theme = createTheme({
  // palette: {
  //   secondary: {
  //     main: "#ff8800",
  //   },
  // },
});

ReactDOM.createRoot(document.getElementById("root")!).render(
  <React.StrictMode>
    <AuthProvider>
      <ThemeProvider theme={theme}>
        <RouterProvider router={router} />
      </ThemeProvider>
    </AuthProvider>
  </React.StrictMode>
);
