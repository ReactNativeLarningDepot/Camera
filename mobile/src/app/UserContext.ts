import {createContext, useContext} from "react";
import {User} from "./models/User";

type UserContextType = {
  user: User | null
  setUser: (newValue: User) => void
}

export const UserContext = createContext<UserContextType>({
  user: null, setUser: () => {
  }
})
export const useUserContext = () => useContext(UserContext)