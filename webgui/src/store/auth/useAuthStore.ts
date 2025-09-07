import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetMe, apiLogin, apiLogout} from "../../api/auth.ts";
import type {TAuthState} from "./types.ts";

const useAuthStore = create<TAuthState>()(
  devtools(
    persist(
      (set, get) => ({
        loggedInStudent: undefined,
        getMe: () => {
          apiGetMe()
          .then(res => res.data)
          .then(res => set(() => ({loggedInStudent: res})))
          .catch(() => set(() => ({loggedInStudent: undefined})));
        },
        login: (email, password) => {
          apiLogin(email, password)
          .then(res => res.status)
          .then(status => {
            if (status === 200) {
              get().getMe();
            }
          })
        },
        logout: () => {
          // Implement logout logic here
          apiLogout().then(() => {
            set(() => ({loggedInStudent: undefined}));
          })
        }
      }),
      {
        name: "auth-store"
      }
    )
  )
)

export default useAuthStore;
