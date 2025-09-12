import styled from "@emotion/styled";
import {ActionIcon, AppShell, Burger, Group, Title, useMantineTheme} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import * as React from "react";
import {MdLogout} from "react-icons/md";
import useAuthStore from "../../store/auth/useAuthStore.ts";
import SearchGroup from "./SearchGroup.tsx";
import ProgressModal from "./statistics/ProgressModal.tsx";


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

  const theme = useMantineTheme();
  const logout = useAuthStore(state => state.logout);


  return (
    <StyledHeader>
      <StyledInnerDiv>
        <Group>
          <Burger opened={opened} onClick={() => {
            onToggleNavBar()
            toggle()
          }} size="sm" hiddenFrom="sm"/>
          <Title fz={theme.fontSizes}>PJ SkillTrack</Title>
        </Group>

        <Group visibleFrom="sm">
          <SearchGroup/>
          <ProgressModal/>
        </Group>
        <ActionIcon variant="light" aria-label="Logout" onClick={logout}>
          <MdLogout/>
        </ActionIcon>
      </StyledInnerDiv>
    </StyledHeader>
  );
}
export default Header;
