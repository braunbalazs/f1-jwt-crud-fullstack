import { Box } from "@mui/material";
import { Navigate } from "react-router-dom";

export default function Home() {
  return <Navigate to={"/teams"}></Navigate>;
}
