import { useState } from "react";
import { api, setCredentials } from "../api";
import { useNavigate } from "react-router-dom";

export default function Login() {
  const [form, setForm] = useState({ username: "", password: "" });
  const navigate = useNavigate();

  const login = async () => {
    try {
      await api.post("/auth/login", form);

      setCredentials(form.username, form.password);

      const me = await api.get("/auth/me");
      sessionStorage.setItem("userId", me.data.id);

      navigate("/home");
    } catch (e) {
      alert("Usuário ou senha incorretos!");
    }
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>

        <h2 style={styles.title}>Login</h2>

        <input
          style={styles.input}
          placeholder="Username"
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />

        <input
          style={styles.input}
          placeholder="Senha"
          type="password"
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />

        <button style={styles.button} onClick={login}>
          Entrar
        </button>

      </div>
    </div>
  );
}

const styles = {
  container: {
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    background: "#f2f2f2",
  },

  card: {
    background: "white",
    padding: "40px",
    borderRadius: "10px",
    width: "330px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    display: "flex",
    flexDirection: "column",
    gap: "15px",
  },

  title: {
    textAlign: "center",
    marginBottom: "10px",
    fontSize: "26px",
    color: "#333",
  },

  input: {
    padding: "12px",
    fontSize: "16px",
    borderRadius: "6px",
    border: "1px solid #ccc",
    outline: "none",
    transition: "0.2s",
  },

  button: {
    marginTop: "10px",
    padding: "12px",
    fontSize: "16px",
    borderRadius: "6px",
    background: "#4caf50",
    color: "white",
    border: "none",
    cursor: "pointer",
    transition: "0.2s",
  },
};
