import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
  auth: {
    username: "admin",
    password: "123456",
  }
});
