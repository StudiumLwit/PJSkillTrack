export type TSkillOverview = {
  id: number,
  name: string,
  description: string,
  note: string,
  statusType: TStatusType,
  lastModified: Date
}

export type TUpdateSkillOverview = {
  note: string,
  statusType: TStatusType,

}

type TSkillStateProps = {
  skills: TSkillOverview[],
  skillSearch: string
}

type TSkillStateActions = {
  getSkillsByFacultyName: (facultyName: string) => void,
  updateSkill: (skillId: number, updateSkillOverview: TUpdateSkillOverview) => void
  updateSkillSearch: (search: string) => void
}

export type TSkillState = TSkillStateProps & TSkillStateActions;

type TStatusType = "UNDEFINED" | "SEEN" | "DONE" | "ROUTINE"
