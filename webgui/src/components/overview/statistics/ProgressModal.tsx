import {ActionIcon, Modal} from "@mantine/core";
import {useDisclosure} from "@mantine/hooks";
import * as React from "react";
import {useEffect} from "react";
import {GiProgression} from "react-icons/gi";
import useStatisticsStore from "../../../store/statistics/useStatisticsStore.ts";
import ProgressBarList from "./ProgressBarList.tsx";

const ProgressModal: React.FC = () => {
  const [opened, {open, close}] = useDisclosure(false);
  const {getStatusByFaculty} = useStatisticsStore();

  useEffect(() => {
    if (opened) {
      getStatusByFaculty();
    }
  }, [opened]);

  return (
    <>
      <Modal opened={opened} onClose={close} title="Fortschritt" size="xl">
        <ProgressBarList/>
      </Modal>
      <ActionIcon variant="light" aria-label="Progress" onClick={open}>
        <GiProgression/>
      </ActionIcon>
    </>
  )
}

export default ProgressModal;
