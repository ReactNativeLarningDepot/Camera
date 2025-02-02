import {ReactNode} from "react";
import {useUserContext} from "./UserContext";

interface Props {
  children: ReactNode
}

const Authorized = ({children}: Props) => {
  const { user } = useUserContext()

  return (
    user && children
  )
}

export default Authorized