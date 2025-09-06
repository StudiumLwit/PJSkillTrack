import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetSkillsByFacultyName} from "../../api/skill.ts";
import type {TSkillState} from "./types.ts";

const useSkillStore = create<TSkillState>()(
  devtools(
    persist(
      (set => ({
          skills: [],
          getSkillsByFacultyName: facultyName => {
            apiGetSkillsByFacultyName(facultyName)
            .then(res => res.data)
            .then(res => set(() => ({skills: res})))
          }
        })
      ), {
        name: "skill-store"
      }
    )
  )
)

export default useSkillStore;
