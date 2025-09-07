import {ActionIcon, Modal, Text} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import * as React from "react";
import {GiProgression} from "react-icons/gi";

const ProgressModal: React.FC = () => {
  const [opened, {open, close}] = useDisclosure(false);

  return (
    <>
      <Modal opened={opened} onClose={close} title="Fortschritt">
        <Text>Hier wird bald dein Fortschritt pro Fachrichtung erscheinen</Text>
      </Modal>
      <ActionIcon variant="light" aria-label="Progress" onClick={open}>
        <GiProgression/>
      </ActionIcon>
    </>
  )
}

export default ProgressModal;
