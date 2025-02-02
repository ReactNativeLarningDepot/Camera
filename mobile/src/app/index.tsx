import UserProvider from "./UserProvider";
import LogIn from "./screens/LogIn";
import Authorized from "./Authorized";
import Main from "./screens/Main";
import UnAuthorized from "./UnAuthorized";

const Index = () => {
  return (
    <UserProvider>
      <Authorized>
        <Main/>
      </Authorized>

      <UnAuthorized>
        <LogIn />
      </UnAuthorized>
    </UserProvider>
  )
}

export default Index
