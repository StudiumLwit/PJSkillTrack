import {api} from "./utils.ts";

export const apiGetSkillsByFacultyName = (facultyName: string) => api.get("/api/skill?facultyName=" + facultyName)
