import {AppShell, Stack} from "@mantine/core";
import * as React from "react";
import SearchGroup from "./SearchGroup.tsx";
import ProgressModal from "./statistics/ProgressModal.tsx";

const HeaderNavBar: React.FC = () => {

  return (
    <AppShell.Navbar p="xs">
      <Stack>
        <SearchGroup/>
        <ProgressModal/>
      </Stack>
    </AppShell.Navbar>
  )
}

export default HeaderNavBar;
