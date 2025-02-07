import {View, Text, TouchableOpacity} from "react-native";
import {useUserContext} from "../UserContext";
import {useEffect, useState} from "react";
import {getHoge, GetHogeResponse} from "../../clients/hoge-client";
import {router} from "expo-router";

const Main = () => {
  const {user} = useUserContext()
  const [hoge, setHoge] = useState<string>('')

  useEffect(() => {
    if (!user) return

    (async () => {
      const response: GetHogeResponse = await getHoge(user)
      setHoge(response.text)
    })()
  }, []);

  return (
    <View>
      <Text>{user?.name}さん、ようこそ！</Text>
      <Text>{hoge}</Text>
      <TouchableOpacity onPress={() => router.push('/screens/Main2')}>
        <Text>Main2へ</Text>
      </TouchableOpacity>
    </View>
  )
}

export default Main