import { useEffect, useState } from "react";
import { api, setCredentials } from "../api";
import PostCard from "../components/PostCard";
import { useNavigate } from "react-router-dom";

export default function Home() {
  const [posts, setPosts] = useState([]);
  const navigate = useNavigate();
  const [userId, setUserId] = useState(null);

  useEffect(() => {
    const storedUserId = sessionStorage.getItem("userId");
    if (storedUserId) {
      setUserId(Number(storedUserId));

      api.get(`/posts/${storedUserId}`).then((res) => setPosts(res.data));
    }
  }, []);

  const goToCreate = () => navigate("/create");

  const handleDelete = async (postId) => {
    const ok = window.confirm("Deseja excluir este post?");
    if (!ok) return;

    await api.delete(`/posts/${postId}`);
    setPosts((prev) => prev.filter((p) => p.id !== postId));
  };

  const logout = () => {
    sessionStorage.clear();
    setCredentials(null, null);
    navigate("/");
  };

  return (
    <div style={styles.container}>
      <div style={styles.content}>

        <div style={styles.topBar}>
          <button style={styles.logoutButton} onClick={logout}>
            Sair
          </button>
        </div>

        <h1 style={styles.title}>Posts</h1>

        <button style={styles.createButton} onClick={goToCreate}>
          + Criar Novo Post
        </button>

        <div style={styles.postsList}>
          {posts.map((post) => (
            <div key={post.id} style={styles.postWrapper}>
              <PostCard post={post} />

              <button
                onClick={() => handleDelete(post.id)}
                style={styles.deleteButton}
                title="Excluir post"
              >
                🗑️
              </button>
            </div>
          ))}
        </div>

      </div>
    </div>
  );
}

const styles = {
  container: {
    background: "#f2f2f2",
    minHeight: "100vh",
    display: "flex",
    justifyContent: "center",
    paddingTop: "40px",
  },

  content: {
    width: "100%",
    maxWidth: "650px",
    padding: "0 20px",
  },

  topBar: {
    display: "flex",
    justifyContent: "flex-end",
    marginBottom: "10px",
  },

  logoutButton: {
    padding: "8px 16px",
    background: "#d32f2f",
    color: "white",
    border: "none",
    borderRadius: "6px",
    cursor: "pointer",
    fontSize: "14px",
    fontWeight: "bold",
    transition: "0.2s",
  },

  title: {
    textAlign: "center",
    marginBottom: "20px",
    fontSize: "32px",
    color: "#333",
  },

  createButton: {
    padding: "12px 20px",
    background: "#4caf50",
    color: "white",
    border: "none",
    borderRadius: "8px",
    cursor: "pointer",
    fontSize: "16px",
    fontWeight: "bold",
    width: "100%",
    marginBottom: "25px",
    transition: "0.2s",
  },

  postsList: {
    display: "flex",
    flexDirection: "column",
    gap: "15px",
  },

  postWrapper: {
    display: "flex",
    alignItems: "center",
    justifyContent: "space-between",
    background: "white",
    borderRadius: "10px",
    padding: "10px 15px",
    boxShadow: "0 3px 8px rgba(0,0,0,0.1)",
  },

  deleteButton: {
    background: "transparent",
    border: "none",
    cursor: "pointer",
    fontSize: "22px",
    color: "#d32f2f",
    marginLeft: "10px",
    transition: "0.2s",
  },
};
