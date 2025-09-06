import useAuthStore from "../../store/auth/useAuthStore.ts";
import Header from "./Header.tsx";
import SkillList from "./SkillList.tsx";

const Overview: React.FC = () => {

  const loggedInStudent = useAuthStore(state => state.loggedInStudent);

  return (
    <div>
      <Header/>
      <h1>{loggedInStudent ? `Hallo, ${loggedInStudent.name}` : "Willkommen"}</h1>
      <SkillList/>
    </div>
  );
};

export default Overview;
