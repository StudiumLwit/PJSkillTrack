import {Button, Group, Popover} from "@mantine/core";
import {useMediaQuery} from "@mantine/hooks";
import type {TSkillOverview} from "../../store/skill/types.ts";
import useSkillStore from "../../store/skill/useSkillStore.ts";
import {fallbackStatusType, statusUi} from "../../utils/statusUi.ts";

type TProps = {
  skill: TSkillOverview
}

const StatusIcon: React.FC<TProps> = ({skill}) => {
  const {statusType} = skill;

  const smallScreen = useMediaQuery("(max-width: 768px)");
  const updateSkill = useSkillStore(state => state.updateSkill)

  const availableStatusTypes = Object.values(statusUi);
  const currentStatusType = availableStatusTypes.find(st => st.key === statusType)
  if (!currentStatusType) {
    console.warn(`Undefined status type '${statusType}' found, will continue with fallback`)
  }
  const {icon: Icon, colorHex, key, label} = currentStatusType ?? fallbackStatusType;

  const onStatusTypeChange = (newStatusType: string) => {
    updateSkill(skill.id, {note: skill.note, statusType: newStatusType})
  }

  return (
    <div onClick={e => e.stopPropagation()}>
      <Popover width={300} trapFocus position="bottom" withArrow shadow="md">
        <Popover.Target>
          {Icon ? <Icon size={smallScreen ? "30px" : "50px"} cursor={"pointer"} strokeWidth={"0.5"} color={colorHex}
                        title={label}/> : null}
        </Popover.Target>
        <Popover.Dropdown>
          <Group>
            {availableStatusTypes.map(t => <Button disabled={t.key === key} fullWidth key={t.key}
                                                   onClick={() => onStatusTypeChange(t.key)}
                                                   color={t.colorHex}>{t.label}</Button>)}
          </Group>
        </Popover.Dropdown>
      </Popover>
    </div>)
}

export default StatusIcon;
