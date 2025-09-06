export type TSkillOverview = {
  name: string,
  description: string,
  note: string,
  statusType: TStatusType,
  lastModified: Date
}

type TSkillStateProps = {
  skills: TSkillOverview[]
}

type TSkillStateActions = {
  getSkillsByFacultyName: (facultyName: string) => void
}

export type TSkillState = TSkillStateProps & TSkillStateActions;

type TStatusType = "UNDEFINED" | "SEEN" | "DONE" | "ROUTINE"
