import {ReactNode, useState} from "react";
import {User} from "./models/User";
import {UserContext} from "./UserContext";

interface Props {
  children: ReactNode
}

const UserContextProvider = ({children}: Props) => {
  const [user, setUser] = useState<User | null>(null)

  return (
    <UserContext.Provider value={{ user, setUser }}>
      { children }
    </UserContext.Provider>
  )
}

export default UserContextProvider