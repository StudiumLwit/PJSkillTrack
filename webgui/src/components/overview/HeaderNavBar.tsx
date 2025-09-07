import {AppShell, Autocomplete, Select, Stack} from "@mantine/core";
import * as React from "react";
import {useEffect} from "react";
import {BiSearch} from "react-icons/bi";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";

const HeaderNavBar: React.FC = () => {

  const updateSkillSearch = useSkillStore(state => state.updateSkillSearch)
  const {activeFaculty, faculties, setActiveFaculty, getFaculties} = useFacultyStore();
  const data = faculties.map(f => f.name);

  useEffect(() => {
      getFaculties();
    }, []
  )

  return (
    <AppShell.Navbar p="xs">
      <Stack>
        <Select searchable value={activeFaculty} allowDeselect={false} onChange={e => setActiveFaculty(e || "")}
                data={data}/>
        <Autocomplete
          placeholder="Search"
          onChange={updateSkillSearch}
          leftSection={<BiSearch size={16}/>}
          data={['React', 'Angular', 'Vue', 'Next.js', 'Riot.js', 'Svelte', 'Blitz.js']}
        />
      </Stack>
    </AppShell.Navbar>
  )
}

export default HeaderNavBar;
