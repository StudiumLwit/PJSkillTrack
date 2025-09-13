import type {AxiosError} from "axios";
import {devtools, persist} from "zustand/middleware";
import {create} from "zustand/react";
import {apiGetMe, apiLogin, apiLogout} from "../../api/auth.ts";
import type {TAuthState} from "./types.ts";

const useAuthStore = create<TAuthState>()(
  devtools(
    persist(
      (set, get) => ({
        loggedInStudent: undefined,
        errorMsg: "",
        getMe: () => {
          apiGetMe()
          .then(res => res.data)
          .then(res => set(() => ({loggedInStudent: res, errorMsg: undefined})))
          .catch(() => set(() => ({loggedInStudent: undefined})));
        },
        login: (email, password) => {
          apiLogin(email, password)
          .then(res => res.status)
          .then(() => {
            get().getMe();
          })
          .catch((e: AxiosError) => {
            const status = e.response?.status;
            if (status) {
              set(() => ({errorMsg: "Ungültige Anmeldedaten"}))
            } else {
              set(() => ({errorMsg: "Es ist ein Fehler aufgetreten"}))
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
