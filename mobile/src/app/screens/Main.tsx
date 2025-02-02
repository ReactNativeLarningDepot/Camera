import {View, Text} from "react-native";
import {useUserContext} from "../UserContext";

const Main = () => {
  const { user } = useUserContext()
  return (
    <View>
      <Text>{user?.name}さん、ようこそ！</Text>
    </View>
  )
}

export default Main