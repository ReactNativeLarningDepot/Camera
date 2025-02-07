import {View, Text} from "react-native";
import {useUserContext} from "../UserContext";
import {useEffect, useState} from "react";
import {getHoge, GetHogeResponse} from "../../clients/hoge-client";

const Main2 = () => {
  return (
    <View>
      <Text>Main2 Page へようこそ</Text>
    </View>
  )
}

export default Main2