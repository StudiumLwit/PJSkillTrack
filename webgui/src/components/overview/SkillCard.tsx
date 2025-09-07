import styled from "@emotion/styled";
import {Accordion, Button, Stack, Text, Textarea, Title, useMantineTheme} from "@mantine/core";
import {useState} from "react";
import type {TSkillOverview} from "../../store/skill/types.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";
import StatusIcon from "./StatusIcon.tsx";

type TProps = {
  skill: TSkillOverview
}

const TopRow = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
`

const SkillCard: React.FC<TProps> = ({skill}) => {
  const {id, name, description, lastModified, note} = skill;

  const [notetext, setNotetext] = useState<string>(note);
  const updateSkill = useSkillStore(state => state.updateSkill)
  const theme = useMantineTheme()


  const onSubmit = () => {
    updateSkill(id, {note: notetext, statusType: skill.statusType})
  }

  return (
    <div onClick={e => e.stopPropagation()}>
      <Accordion.Control>
        <TopRow>
          <Title order={2} lineClamp={2} fz={theme.fontSizes}>{name}</Title>
          <StatusIcon skill={skill}/>
        </TopRow>
      </Accordion.Control>
      <Accordion.Panel>
        <Stack>
          <Text ta="justify" style={{whiteSpace: "pre-line"}}>{description}</Text>
          <Textarea autosize minRows={3} value={notetext} onChange={e => setNotetext(e.target.value)}/>
          <Text size="sm" c="dimmed" ta="left">
            Letzter Statuswechsel: {lastModified ? new Date(lastModified).toLocaleDateString() : "nie"}
          </Text>
          <Button onClick={onSubmit} disabled={notetext === note}>Speichern</Button>
        </Stack>
      </Accordion.Panel>
    </div>
  )
}

export default SkillCard;
