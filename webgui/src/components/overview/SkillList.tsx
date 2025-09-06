import {useEffect} from "react";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";

const SkillList: React.FC = () => {

  const activeFaculty = useFacultyStore(state => state.activeFaculty)
  const {skills, getSkillsByFacultyName} = useSkillStore()

  useEffect(() => {
    activeFaculty && getSkillsByFacultyName(activeFaculty)
  }, [activeFaculty])

  return (
    <div>
      {skills.map(s => (<p>{s.name}</p>))}
    </div>
  )
}

export default SkillList;
