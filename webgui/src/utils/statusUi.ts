import {CiCircleQuestion} from "react-icons/ci";
import {FaCheck, FaCheckDouble, FaQuestionCircle, FaRegEye} from "react-icons/fa";
import type {TStatusType} from "../store/skill/types.ts";

export const statusUi: Record<string, TStatusType> = {
  UNDEFINED: {key: "UNDEFINED", label: "Offen", colorHex: "#d4d4d4", icon: props => FaQuestionCircle(props)},
  SEEN: {key: "SEEN", label: "Gesehen", colorHex: "#a5b4fc", icon: props => FaRegEye(props)},
  DONE: {key: "DONE", label: "Durchgeführt", colorHex: "#99d88e", icon: props => FaCheck(props)},
  ROUTINE: {
    key: "ROUTINE",
    label: "Routinemäßig durchgeführt",
    colorHex: "#80b577",
    icon: props => FaCheckDouble(props)
  },
};

export const fallbackStatusType: TStatusType = {
  key: "NULL",
  label: "Undefinierter Status",
  colorHex: "#c3c9c1",
  icon: props => CiCircleQuestion(props)
}
