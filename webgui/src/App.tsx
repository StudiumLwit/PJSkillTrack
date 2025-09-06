import {MantineProvider} from "@mantine/core";
import {useEffect} from 'react'
import './App.css'
import {Login} from "./components/authentication/Login.tsx";
import Dashboard from "./components/Dashboard.tsx";
import useAuthStore from "./store/auth/useAuthStore.ts";
import theme from "./theme.ts";
import '@mantine/core/styles.css';

function App() {

  const {loggedInStudent, getMe} = useAuthStore();

  useEffect(() => {
    getMe();
  }, []);

  return (
    <MantineProvider theme={theme}>
      {loggedInStudent ?
        <Dashboard/> :
        <Login/>
      }
    </MantineProvider>
  )
}

export default App
