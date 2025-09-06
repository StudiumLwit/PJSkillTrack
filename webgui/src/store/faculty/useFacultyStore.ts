import {create} from "zustand/react";
import type {TFacultyState} from "./types.ts";
import {devtools, persist} from "zustand/middleware";


const useFacultyStore = create<TFacultyState>()(
    devtools(
        persist(
            (set) => ({
                faculties: [],
                activeFaculty: undefined,

                getFaculties: async () => {
                    const response = await fetch("/api/faculties");
                    const data = await response.json();
                    set({faculties: data});
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
