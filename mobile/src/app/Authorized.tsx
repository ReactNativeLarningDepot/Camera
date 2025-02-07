import {ReactNode} from "react";
import {useUserContext} from "./UserContext";
import LogIn from "./screens/LogIn";

interface Props {
  children: ReactNode
}

const Authorized = ({children}: Props) => {
  const { user } = useUserContext()

  return (
    user ? children : <LogIn />
  )
}

export default Authorized