import {api} from "./utils.ts";

export const apiGetMe = async () => {
  return api.get("/api/auth/me")
}

export const apiLogin = async (email: string, password: string) => {
  return api.post("/login", new URLSearchParams({username: email, password}), {
    headers: {
      'Content-Type': 'application/x-www-form-urlencoded'
    }
  });
}

export const apiLogout = async () => {
  return api.post("/logout")
}
