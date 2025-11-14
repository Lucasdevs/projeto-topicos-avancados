// src/App.jsx
import { useEffect, useState } from "react";
import {
  getAllPosts,
  getPostsByUserId,
  createPost,
} from "./services/postService";

function App() {
  const [posts, setPosts] = useState([]);
  const [loading, setLoading] = useState(false);
  const [userFilterId, setUserFilterId] = useState("");
  const [form, setForm] = useState({
    title: "",
    message: "",
    userId: "",
  });
  const [error, setError] = useState("");
 
  async function loadAllPosts() {
    try {
      setLoading(true);
      setError("");
      const data = await getAllPosts();
      setPosts(data);
    } catch (e) {
      console.error(e);
      setError("Erro ao carregar posts.");
    } finally {
      setLoading(false);
    }
  }

  async function handleFilterByUser(e) {
    e.preventDefault();
    if (!userFilterId) {
      loadAllPosts();
      return;
    }

    try {
      setLoading(true);
      setError("");
      const data = await getPostsByUserId(userFilterId);
      setPosts(data);
    } catch (e) {
      console.error(e);
      setError("Erro ao buscar posts por usuário.");
    } finally {
      setLoading(false);
    }
  }

  async function handleCreatePost(e) {
    e.preventDefault();
    try {
      setLoading(true);
      setError("");

      const dto = {
        // ajuste os campos para bater com seu PostCreateDTO
        title: form.title,
        message: form.message,
        userId: Number(form.userId),
      };

      await createPost(dto);
      setForm({ title: "", message: "", userId: "" });
      // recarrega lista
      loadAllPosts();
    } catch (e) {
      console.error(e);
      setError("Erro ao criar post.");
    } finally {
      setLoading(false);
    }
  }

  useEffect(() => {
    loadAllPosts();
  }, []);

  function handleChangeForm(e) {
    const { name, value } = e.target;
    setForm((prev) => ({ ...prev, [name]: value }));
  }

  return (
    <div style={{ maxWidth: 800, margin: "0 auto", padding: 20 }}>
      <h1>Rede Social - Posts</h1>

      {error && (
        <div style={{ color: "red", marginBottom: 10 }}>
          {error}
        </div>
      )}

      {/* Filtro por usuário */}
      <section style={{ marginBottom: 30 }}>
        <h2>Filtrar posts por usuário</h2>
        <form onSubmit={handleFilterByUser} style={{ display: "flex", gap: 10 }}>
          <input
            type="number"
            placeholder="ID do usuário"
            value={userFilterId}
            onChange={(e) => setUserFilterId(e.target.value)}
          />
          <button type="submit">Buscar</button>
          <button type="button" onClick={loadAllPosts}>
            Limpar filtro
          </button>
        </form>
      </section>

      {/* Form de criação */}
      <section style={{ marginBottom: 30 }}>
        <h2>Criar novo post</h2>
        <form
          onSubmit={handleCreatePost}
          style={{ display: "flex", flexDirection: "column", gap: 10 }}
        >
          <input
            type="text"
            name="title"
            placeholder="Título"
            value={form.title}
            onChange={handleChangeForm}
            required
          />
          <textarea
            name="message"
            placeholder="Conteúdo"
            value={form.message}
            onChange={handleChangeForm}
            rows={3}
            required
          />
          <input
            type="number"
            name="userId"
            placeholder="ID do usuário"
            value={form.userId}
            onChange={handleChangeForm}
            required
          />
          <button type="submit" disabled={loading}>
            {loading ? "Salvando..." : "Salvar post"}
          </button>
        </form>
      </section>

      {/* Lista de posts */}
      <section>
        <h2>Posts</h2>
        {loading && <p>Carregando...</p>}
        {!loading && posts.length === 0 && <p>Nenhum post encontrado.</p>}
        <ul style={{ listStyle: "none", padding: 0 }}>
          {posts.map((post) => (
            <li
              key={post.id}
              style={{
                border: "1px solid #ddd",
                borderRadius: 8,
                padding: 10,
                marginBottom: 10,
              }}
            >
              <h3>{post.title}</h3>
              <p>{post.message}</p>
              <small>Usuário ID: {post.userId}</small>
            </li>
          ))}
        </ul>
      </section>
    </div>
  );
}

export default App;
