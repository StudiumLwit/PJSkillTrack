import styled from "@emotion/styled";
import {ActionIcon, AppShell, Autocomplete, Burger, Group, Select} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import * as React from "react";
import {useEffect} from "react";
import {BiSearch} from "react-icons/bi";
import {MdLogout} from "react-icons/md";
import useAuthStore from "../../store/auth/useAuthStore.ts";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";


const StyledHeader = styled(AppShell.Header)`
    height: 56px;
    margin-bottom: 120px;
    background-color: var(--mantine-color-body);
    border-bottom: 1px solid light-dark(var(--mantine-color-gray-3), var(--mantine-color-dark-4));
    padding-left: var(--mantine-spacing-md);
    padding-right: var(--mantine-spacing-md);
`

const StyledInnerDiv = styled.div`
    height: 56px;
    display: flex;
    justify-content: space-between;
    align-items: center;
`

type TProps = {
  onToggleNavBar: () => void
}

const Header: React.FC<TProps> = ({onToggleNavBar}) => {
  const [opened, {toggle}] = useDisclosure(false);

  const logout = useAuthStore(state => state.logout);
  const updateSkillSearch = useSkillStore(state => state.updateSkillSearch)
  const {activeFaculty, faculties, setActiveFaculty, getFaculties} = useFacultyStore();
  const data = faculties.map(f => f.name);

  useEffect(() => {
      getFaculties();
    }, []
  )

  return (
    <StyledHeader>
      <StyledInnerDiv>
        <Group>
          <Burger opened={opened} onClick={() => {
            onToggleNavBar()
            toggle()
          }} size="sm" hiddenFrom="sm"/>
          <h1>PJ SkillTrack</h1>
        </Group>

        <Group visibleFrom="sm">
          <Select searchable value={activeFaculty} allowDeselect={false} onChange={e => setActiveFaculty(e || "")}
                  data={data}/>
          <Autocomplete
            placeholder="Search"
            onChange={updateSkillSearch}
            leftSection={<BiSearch size={16}/>}
            data={['React', 'Angular', 'Vue', 'Next.js', 'Riot.js', 'Svelte', 'Blitz.js']}
            visibleFrom="xs"
          />
        </Group>
        <ActionIcon variant="light" aria-label="Settings" onClick={logout}>
          <MdLogout/>
        </ActionIcon>
      </StyledInnerDiv>
    </StyledHeader>
  );
}
export default Header;
