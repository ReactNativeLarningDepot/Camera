import {ReactNode} from "react";
import {useUserContext} from "./UserContext";

interface Props {
  children: ReactNode
}

const UnAuthorized = ({children}: Props) => {
  const { user } = useUserContext()

  return (
    user == null && children
  )
}

export default UnAuthorized