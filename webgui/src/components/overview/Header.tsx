import {ActionIcon, Autocomplete, Burger, Group, Select} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import * as React from "react";
import {useEffect} from "react";
import {BiSearch} from "react-icons/bi";
import {MdLogout} from "react-icons/md";
import styled from "styled-components";
import useAuthStore from "../../store/auth/useAuthStore.ts";
import useFacultyStore from "../../store/faculty/useFacultyStore.ts";


const Header: React.FC = () => {
  const [opened, {toggle}] = useDisclosure(false);

  const logout = useAuthStore(state => state.logout);
  const {activeFaculty, faculties, setActiveFaculty, getFaculties} = useFacultyStore();
  const data = faculties.map(f => f.name);

  useEffect(() => {
      getFaculties();
    }, []
  )

  const StyledHeader = styled.header`
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

  return (
    <StyledHeader>
      <StyledInnerDiv>
        <Group>
          <Burger opened={opened} onClick={toggle} size="sm" hiddenFrom="sm"/>
          <h1>PJ SkillTrack</h1>
        </Group>

        <Group>
          <Select searchable value={activeFaculty} allowDeselect={false} onChange={e => setActiveFaculty(e || "")}
                  data={data}/>
          <Autocomplete
            placeholder="Search"
            leftSection={<BiSearch size={16}/>}
            data={['React', 'Angular', 'Vue', 'Next.js', 'Riot.js', 'Svelte', 'Blitz.js']}
            visibleFrom="xs"
          />
          <ActionIcon variant="light" aria-label="Settings" onClick={logout}>
            <MdLogout/>
          </ActionIcon>
        </Group>
      </StyledInnerDiv>
    </StyledHeader>
  );
}
export default Header;
