import axios from "axios";

export const apiGetMe = async () => {
    return axios.get("/api/auth/me")
}
