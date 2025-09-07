import type {IconType} from "react-icons";

export type TSkillOverview = {
  id: number,
  name: string,
  description: string,
  note: string,
  statusType: string,
  lastModified: Date
}

export type TUpdateSkillOverview = {
  note: string,
  statusType: string,

}

type TSkillStateProps = {
  skills: TSkillOverview[],
  skillSearch: string,
}

type TSkillStateActions = {
  getSkillsByFacultyName: (facultyName: string) => void,
  updateSkill: (skillId: number, updateSkillOverview: TUpdateSkillOverview) => void
  updateSkillSearch: (search: string) => void
}

export type TSkillState = TSkillStateProps & TSkillStateActions;

export type TStatusType = {
  key: string,
  label: string,
  colorHex: string,
  icon?: IconType
}
