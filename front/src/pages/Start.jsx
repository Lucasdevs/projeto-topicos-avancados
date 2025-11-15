import { useNavigate } from "react-router-dom";

export default function Start() {
  const navigate = useNavigate();

  return (
    <div style={styles.container}>
      <div style={styles.card}>

        <h1 style={styles.title}>Bem-vindo 👋</h1>
        <p style={styles.subtitle}>Escolha uma opção para continuar</p>

        <button
          style={styles.primaryButton}
          onClick={() => navigate("/register")}
        >
          Cadastrar Novo Usuário
        </button>

        <button
          style={styles.secondaryButton}
          onClick={() => navigate("/login")}
        >
          Login
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
    padding: "40px",
    borderRadius: "12px",
    width: "100%",
    maxWidth: "380px",
    boxShadow: "0 4px 12px rgba(0,0,0,0.15)",
    display: "flex",
    flexDirection: "column",
    gap: "18px",
    textAlign: "center",
  },

  title: {
    fontSize: "28px",
    color: "#333",
    marginBottom: "5px",
  },

  subtitle: {
    fontSize: "15px",
    color: "#555",
    marginBottom: "20px",
  },

  primaryButton: {
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

  secondaryButton: {
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
