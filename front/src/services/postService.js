import { api } from "../api";

export async function getAllPosts() {
  const response = await api.get("/posts");
  return response.data; 
}

export async function getPostsByUserId(userId) {
  const response = await api.get(`/posts/${userId}`);
  return response.data;
}

export async function createPost(postCreateDTO) {
  const response = await api.post("/posts", postCreateDTO);
  return response.data; 
}
