export type TStudent = {
  name: string;
  email: string;
};

export type TAuthStateActions = {
  getMe: () => void;
  login: (email: string, password: string) => void;
  logout: () => void;
}

export type TAuthStateProps = {
  loggedInStudent?: TStudent
};

export type TAuthState = TAuthStateProps & TAuthStateActions;
