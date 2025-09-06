import type {TUpdateSkillOverview} from "../store/skill/types.ts";
import {api} from "./utils.ts";

export const apiGetSkillsByFacultyName = (facultyName: string) => api.get("/api/skill?facultyName=" + facultyName)

export const apiUpdateSkill = (skillId: number, updateSkillOverview: TUpdateSkillOverview) => api.put("/api/skill/" + skillId, updateSkillOverview)
