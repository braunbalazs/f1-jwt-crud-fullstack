import axios from "axios";
import {
  ReactNode,
  createContext,
  useContext,
  useEffect,
  useMemo,
  useState,
} from "react";

type ContextType = {
  token: string | null;
  setToken: (newToken: string | null) => void;
};

type AuthProviderProps = {
  children: ReactNode;
};

const AuthContext = createContext<ContextType | null>(null);

function AuthProvider({ children }: AuthProviderProps) {
  const [token, setToken_] = useState<string | null>(
    localStorage.getItem("F1_token")
  );

  function setToken(newToken: string | null) {
    setToken_(newToken);
  }

  useEffect(() => {
    if (token) {
      axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;
      localStorage.setItem("F1_token", token);
    } else {
      delete axios.defaults.headers.common["Authorization"];
      localStorage.removeItem("F1_token");
    }
  }, [token]);

  const contextValue = useMemo(
    () => ({
      token,
      setToken,
    }),
    [token]
  );

  return (
    <AuthContext.Provider value={contextValue}>{children}</AuthContext.Provider>
  );
}

export function useAuth() {
  const authContext = useContext(AuthContext);
  if (authContext == null) {
    throw new Error("useAuth must be used within provider");
  }
  return authContext;
}

export default AuthProvider;
