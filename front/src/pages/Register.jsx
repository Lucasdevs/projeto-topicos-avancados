import { useState } from "react";
import { api, setCredentials } from "../api";
import { useNavigate } from "react-router-dom";

export default function Register() {
  const [form, setForm] = useState({ username: "", password: "", name: "" });
  const navigate = useNavigate();

  const register = async () => {

    await api.post("/auth/logout", form);
    await api.post("/auth/register", form);

    setCredentials(form.username, form.password);

    const me = await api.get("/auth/me");
    sessionStorage.setItem("userId", me.data.id);

    navigate("/home");
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>

        <h2 style={styles.title}>Criar Conta</h2>

        <input
          style={styles.input}
          placeholder="Nome"
          onChange={(e) => setForm({ ...form, name: e.target.value })}
        />

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

        <button style={styles.button} onClick={register}>
          Criar conta
        </button>

      </div>
    </div>
  );
}

const styles = {
  container: {
    height: "100vh",
    background: "#f2f2f2",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    padding: "20px",
  },

  card: {
    background: "white",
    padding: "35px",
    borderRadius: "10px",
    width: "100%",
    maxWidth: "360px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    display: "flex",
    flexDirection: "column",
    gap: "14px",
  },

  title: {
    textAlign: "center",
    marginBottom: "8px",
    fontSize: "24px",
    color: "#333",
  },

  input: {
    padding: "12px",
    fontSize: "15px",
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
    fontWeight: "bold",
    transition: "0.2s",
  },
};
