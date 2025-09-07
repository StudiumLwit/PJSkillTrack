import styled from "@emotion/styled";
import {Accordion, Button, Textarea} from "@mantine/core";
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

const LastModifiedDate = styled.p`
    color: dimgrey;
`

const SkillCard: React.FC<TProps> = ({skill}) => {

  const {id, name, description, lastModified, note} = skill;

  const [notetext, setNotetext] = useState<string>(note);

  const updateSkill = useSkillStore(state => state.updateSkill)


  const onSubmit = () => {
    updateSkill(id, {note: notetext, statusType: skill.statusType})
  }


  return (
    <div onClick={e => e.stopPropagation()}>
      <Accordion.Control>
        <TopRow>
          <h2>{name}</h2>
          <StatusIcon skill={skill}/>
        </TopRow>
      </Accordion.Control>
      <Accordion.Panel>
        <div>
          <p>{description}</p>
          <Textarea autosize minRows={3} value={notetext} onChange={e => setNotetext(e.target.value)}/>
          <LastModifiedDate>Letztes
            Statusupdate: {lastModified ? new Date(lastModified).toLocaleString() : "Nie"}</LastModifiedDate>
          <Button onClick={onSubmit}>Speichern</Button>
        </div>
      </Accordion.Panel>
    </div>
  )
}

export default SkillCard;
