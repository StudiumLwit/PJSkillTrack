import useAuthStore from "../store/auth/useAuthStore.ts";

const Dashboard: React.FC = () => {

  const loggedInStudent = useAuthStore(state => state.loggedInStudent);
  const logout = useAuthStore(state => state.logout);

  return (
    <div>
      <h1>{loggedInStudent ? `Hallo, ${loggedInStudent.name}` : "Willkommen"}</h1>
      <button onClick={logout}>Logout</button>
    </div>
  );
};

export default Dashboard;
