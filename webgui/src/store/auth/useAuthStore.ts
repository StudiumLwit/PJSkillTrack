import {create} from "zustand/react";
import type {TAuthState} from "./types.ts";
import {devtools, persist} from "zustand/middleware";
import {apiGetMe} from "../../api/auth.ts";

const useAuthStore = create<TAuthState>()(
  devtools(
    persist(
      (set) => ({
        loggedInStudent: undefined,
        getMe: () => {
          apiGetMe()
          .then(res => res.data)
          .then(res => set(() => ({loggedInStudent: res})))
          .catch(() => set(() => ({loggedInStudent: undefined})));
        }
      }),
      {
        name: "auth-store"
      }
    )
  )
)

export default useAuthStore;
