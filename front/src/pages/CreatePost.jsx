import { useState } from "react";
import { api } from "../api";
import { useNavigate } from "react-router-dom";

export default function CreatePost() {
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  const create = async () => {
    const me = await api.get("/auth/me");
    const userId = me.data.userId;

    await api.post("/posts", {
      userId,
      message,
    });

    alert("Post criado com sucesso!");
    navigate("/home");
  };

  return (
    <div style={styles.container}>
      <div style={styles.card}>

        <h2 style={styles.title}>Criar Novo Post</h2>

        <textarea
          placeholder="Digite sua mensagem..."
          onChange={(e) => setMessage(e.target.value)}
          style={styles.textarea}
        />

        <button onClick={create} style={styles.button}>
          Publicar
        </button>

      </div>
    </div>
  );
}

const styles = {
  container: {
    background: "#f2f2f2",
    height: "100vh",
    display: "flex",
    justifyContent: "center",
    alignItems: "center",
    padding: "20px",
  },

  card: {
    background: "white",
    padding: "30px",
    borderRadius: "12px",
    width: "100%",
    maxWidth: "500px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    display: "flex",
    flexDirection: "column",
    gap: "15px",
  },

  title: {
    textAlign: "center",
    fontSize: "26px",
    color: "#333",
    marginBottom: "10px",
  },

  textarea: {
    width: "100%",
    height: "140px",
    padding: "12px",
    fontSize: "16px",
    borderRadius: "8px",
    border: "1px solid #ccc",
    outline: "none",
    resize: "none",
    transition: "0.2s",
  },

  button: {
    marginTop: "10px",
    padding: "12px",
    fontSize: "16px",
    borderRadius: "6px",
    background: "#2196F3",
    color: "white",
    border: "none",
    cursor: "pointer",
    fontWeight: "bold",
    transition: "0.2s",
  },
};
