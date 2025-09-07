import {AppShell} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import Header from "./Header.tsx";
import HeaderNavBar from "./HeaderNavBar.tsx";
import SkillList from "./SkillList.tsx";

const Overview: React.FC = () => {
  const [opened, {toggle}] = useDisclosure(false);

  return (
    <AppShell
      padding="md"
      header={{height: 60}}
      navbar={{
        width: 300,
        breakpoint: 'sm',
        collapsed: {mobile: !opened, desktop: true},
      }}
    >
      <Header onToggleNavBar={toggle}/>
      <HeaderNavBar/>
      <SkillList/>
    </AppShell>
  );
};

export default Overview;
