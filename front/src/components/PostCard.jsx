
export default function PostCard({ post }) {
  return (
    <div style={{ border: "1px solid #ccc", margin: 10, padding: 10 }}>
      <h3>{post.userName ?? "Usuário"}</h3>
      <p>{post.message}</p>
    </div>
  );
}
