import {Autocomplete, Select} from "@mantine/core";
import * as React from "react";
import {useEffect} from "react";
import {BiSearch} from "react-icons/bi";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";

const SearchGroup: React.FC = () => {

  const {updateSkillSearch, skills} = useSkillStore()
  const {activeFaculty, faculties, setActiveFaculty, getFaculties} = useFacultyStore();

  const facultyNames = faculties.map(f => f.name);
  const skillNames = skills.map(s => s.name)

  useEffect(() => {
      getFaculties();
    }, []
  )

  return <>
    <Select searchable value={activeFaculty} placeholder="Fachrichtung auswählen" allowDeselect={false}
            onChange={(e) => setActiveFaculty(e || "")}
            data={facultyNames}/>
    <Autocomplete
      placeholder="Skills durchsuchen"
      onChange={updateSkillSearch}
      leftSection={<BiSearch size={16}/>}
      data={skillNames}
    />
  </>;
}

export default SearchGroup;
