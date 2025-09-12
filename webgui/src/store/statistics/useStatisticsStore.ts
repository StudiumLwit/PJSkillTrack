import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetStatusByFaculty} from "../../api/statistics.ts";
import type {TStatisticsState} from "./types.ts";

const useStatisticsStore = create<TStatisticsState>()(
  devtools(
    persist(
      set => ({
        statusByFaculty: {},
        getStatusByFaculty: () => apiGetStatusByFaculty()
        .then(res => res.data)
        .then(res => set(() => ({statusByFaculty: res})))
      }), {
        name: "statistics-store"
      }
    )
  )
)

export default useStatisticsStore;
