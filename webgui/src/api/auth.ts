import {api} from "./utils.ts";

export const apiGetMe = async () => api.get("/api/auth/me")

export const apiLogin = async (email: string, password: string) => api.post("/login", new URLSearchParams({
  username: email,
  password
}), {
  headers: {
    'Content-Type': 'application/x-www-form-urlencoded'
  }
});

export const apiLogout = async () => api.post("/logout")

