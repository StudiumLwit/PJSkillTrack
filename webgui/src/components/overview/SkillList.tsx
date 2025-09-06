import {Accordion} from "@mantine/core";
import {useEffect} from "react";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";
import SkillCard from "./SkillCard.tsx";

const SkillList: React.FC = () => {

  const activeFaculty = useFacultyStore(state => state.activeFaculty)
  const {skills, getSkillsByFacultyName, skillSearch} = useSkillStore()

  useEffect(() => {
    activeFaculty && getSkillsByFacultyName(activeFaculty)
  }, [activeFaculty])

  return (
    <Accordion variant={"separated"} chevronPosition={"left"} chevronIconSize={"32px"}>
      {skills.filter(s => s.name.toLowerCase().includes(skillSearch.toLowerCase())).map(s => (
        <Accordion.Item key={s.name} value={s.name}>
          <SkillCard skill={s}/>
        </Accordion.Item>
      ))}
    </Accordion>
  )
}

export default SkillList;
