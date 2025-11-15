import { Routes, Route, Navigate } from "react-router-dom";
import Start from "./pages/Start";
import Login from "./pages/Login";
import Register from "./pages/Register";
import Home from "./pages/Home";
import CreatePost from "./pages/CreatePost";

export default function App() {
  return (
    <Routes>
      <Route path="/" element={<Start />} />

      <Route path="/login" element={<Login />} />
      <Route path="/register" element={<Register />} />
      <Route path="/home" element={<Home />} />
      <Route path="/create" element={<CreatePost />} />

      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
}
