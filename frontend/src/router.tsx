import { createBrowserRouter } from "react-router-dom";
import HomeLayout from "./HomeLayout";
import Home from "./pages/Home";
import Teams from "./pages/Teams";
import Login from "./pages/Login";
import TeamDetail from "./pages/TeamDetail";
import { ProtectedRoute } from "./components/ProtectedRoute";

export const router = createBrowserRouter([
  {
    path: "/",
    element: <HomeLayout />,
    children: [
      { index: true, element: <Home /> },
      { path: "teams", element: <Teams /> },
      {
        path: "teams/new",
        element: <ProtectedRoute />,
        children: [{ index: true, element: <TeamDetail /> }],
      },
      {
        path: "/teams/:teamId",
        element: <ProtectedRoute />,
        children: [{ index: true, element: <TeamDetail /> }],
      },
      { path: "login", element: <Login /> },
    ],
  },
]);
