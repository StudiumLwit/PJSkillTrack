import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetSkillsByFacultyName, apiUpdateSkill} from "../../api/skill.ts";
import useFacultyStore from "../faculty/useFacultyStore.ts";
import type {TSkillState} from "./types.ts";

const useSkillStore = create<TSkillState>()(
  devtools(
    persist(
      ((set, get) => ({
          skills: [],
          skillSearch: "",
          getSkillsByFacultyName: facultyName => {
            apiGetSkillsByFacultyName(facultyName)
            .then(res => res.data)
            .then(res => set(() => ({skills: res})))
          },
          updateSkill: (skillId, updateSkillOverview) => {
            apiUpdateSkill(skillId, updateSkillOverview)
            .then(res => res.data)
            .then(_ => get().getSkillsByFacultyName(useFacultyStore.getState().activeFaculty || ""))
          },
          updateSkillSearch: search => set(() => ({skillSearch: search}))
        })
      ), {
        name: "skill-store"
      }
    )
  )
)

export default useSkillStore;
