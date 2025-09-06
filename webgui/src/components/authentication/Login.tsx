import styled from "@emotion/styled";
import {Button, Container, Paper, PasswordInput, TextInput, Title} from "@mantine/core";
import {useState} from "react";
import useAuthStore from "../../store/auth/useAuthStore.ts";

const StyledTitle = styled(Title)`
    font-family: Outfit, var(--mantine-font-family);
    font-weight: 500;
`;

export function Login() {

  const login = useAuthStore(state => state.login);

  const [email, setEmail] = useState<string>("");
  const [password, setPassword] = useState<string>("");

  const onSubmit = () => {
    login(email, password);
  }

  return (
    <Container size={420} my={40}>
      <StyledTitle ta="center">
        Willkommen zurück!
      </StyledTitle>


      <Paper withBorder shadow="sm" p={22} mt={30} radius="md">
        <TextInput label="Email" placeholder="du@pjskilltrack.de" required radius="md"
                   onChange={e => setEmail(e.target.value)}/>
        <PasswordInput label="Passwort" placeholder="Dein Passwort" required mt="md" radius="md"
                       onChange={e => setPassword(e.target.value)}/>
        <Button fullWidth mt="xl" radius="md" onClick={onSubmit}>
          Anmelden
        </Button>
      </Paper>
    </Container>
  );
}
