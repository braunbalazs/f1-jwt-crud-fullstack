import { Navigate, Outlet, useLocation } from "react-router-dom";
import { useAuth } from "../auth/AuthProvider";

export function ProtectedRoute() {
  const location = useLocation();

  const { token } = useAuth();
  if (token == null) {
    return (
      <Navigate
        to={`/login?path=${location.pathname}`}
        state={{ from: location.pathname }}
        replace
      />
    );
  }
  return <Outlet />;
}
