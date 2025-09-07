import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetFaculties} from "../../api/faculty.ts";
import type {TFacultyState} from "./types.ts";


const useFacultyStore = create<TFacultyState>()(
  devtools(
    persist(
      (set) => ({
        faculties: [],
        activeFaculty: undefined,

        getFaculties: async () => {
          apiGetFaculties()
          .then(res => res.data)
          .then(res => set(() => ({faculties: res})))
          .catch(() => set(() => ({faculties: []})));
        },
        setActiveFaculty: (faculty: string) => set({activeFaculty: faculty})
      }),
      {
        name: "faculty-store"
      }
    )
  )
)

export default useFacultyStore;
