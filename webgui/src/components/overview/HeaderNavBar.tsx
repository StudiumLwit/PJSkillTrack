import {AppShell, Stack} from "@mantine/core";
import * as React from "react";
import SearchGroup from "./SearchGroup.tsx";

const HeaderNavBar: React.FC = () => {

  return (
    <AppShell.Navbar p="xs">
      <Stack>
        <SearchGroup/>
      </Stack>
    </AppShell.Navbar>
  )
}

export default HeaderNavBar;
