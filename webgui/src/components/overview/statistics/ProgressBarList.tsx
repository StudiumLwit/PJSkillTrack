import {Progress, Stack, Title, Tooltip, useMantineTheme} from "@mantine/core";
import * as React from "react";
import useStatisticsStore from "../../../store/statistics/useStatisticsStore.ts";
import {statusUi} from "../../../utils/statusUi.ts";

const ProgressBarList: React.FC = () => {

  const theme = useMantineTheme();
  const statusByFaculty = useStatisticsStore(state => state.statusByFaculty);

  return (
    <Stack>

      {Object.entries(statusByFaculty).map(([facultyName, statusToCount]) => {
        const totalCount = Object.values(statusToCount).reduce((a, b) => a + b)
        return (
          <>
            <Title fz={theme.fontSizes}>{facultyName}</Title>
            <Progress.Root size={"xl"}>
              {Object.entries(statusToCount).map(([status, count]) => {
                  const countRelativeToHundred = count / totalCount * 100
                  const {colorHex, label} = statusUi[status];
                  return (
                    <Tooltip label={`${label}: ${count} (${countRelativeToHundred}%)`}>
                      <Progress.Section value={countRelativeToHundred} color={colorHex}/>
                    </Tooltip>
                  )
                }
              )}
            </Progress.Root>
          </>
        )
      })}
    </Stack>
  )
}

export default ProgressBarList;
