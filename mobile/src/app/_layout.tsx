import {Stack} from "expo-router";
import UserProvider from "./UserProvider";
import Authorized from "./Authorized";

const Layout = () => {
  return (
    <UserProvider>
      <Authorized>
        <Stack screenOptions={{
          headerStyle: {
            backgroundColor: '#467FD3'
          },
          headerTintColor: '#FFFFFF',
          headerTitle: 'RungramReWrite',
          headerBackTitle: 'Back',
          headerTitleStyle: {
            fontSize: 22,
            fontWeight: 'bold'
          }
        }}/>
      </Authorized>
    </UserProvider>
  )
}

export default Layout
