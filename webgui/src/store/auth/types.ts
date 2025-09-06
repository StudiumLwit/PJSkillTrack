export type TStudent = {
    name: string;
    email: string;
};

export type TAuthStateActions = {
    getMe: () => void;
}

export type TAuthStateProps = {
    loggedInStudent?: TStudent
};

export type TAuthState = TAuthStateProps & TAuthStateActions;
