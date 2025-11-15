import axios from "axios";

let username = null;
let password = null;

export function setCredentials(u, p) {
  username = u;
  password = p;
}

export const api = axios.create({
  baseURL: "http://localhost:8080",
});

api.interceptors.request.use(config => {
  if (username && password) {
    const token = btoa(`${username}:${password}`);
    config.headers.Authorization = `Basic ${token}`;
  }
  return config;
});
