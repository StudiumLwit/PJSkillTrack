export type TFaculty = {
    name: string;
}

export type TFacultyStateProps = {
    faculties: TFaculty[];
    activeFaculty?: string;
}

export type TFacultyStateActions = {
    getFaculties: () => void;
    setActiveFaculty: (faculty: string) => void;
}

export type TFacultyState = TFacultyStateProps & TFacultyStateActions;
