import type {TStringMap} from "../../utils/types.ts";

export type TStatusByFaculty = TStringMap<TStringMap<number>>

type TStatisticsStateProps = {
  statusByFaculty: TStatusByFaculty;
}

type TStatisticsStateActions = {
  getStatusByFaculty: () => void
}

export type TStatisticsState = TStatisticsStateProps & TStatisticsStateActions;
