import {api} from "./utils.ts";

export const apiGetStatusByFaculty = () => api.get("/api/statistics/statusPerFaculty")
