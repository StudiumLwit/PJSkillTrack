import {MantineProvider} from "@mantine/core";
import {useEffect} from 'react'
import './App.css'
import {Login} from "./components/authentication/Login.tsx";
import Overview from "./components/overview/Overview.tsx";
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
        <Overview/> :
        <Login/>
      }
    </MantineProvider>
  )
}

export default App
