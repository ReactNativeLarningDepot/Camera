import axios from "axios";
import {Platform} from "react-native";

const BASE_URL = Platform.OS === "ios" ? process.env.EXPO_PUBLIC_HOST_URL : "http://10.0.2.2:8080/"

export const baseClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    accept: "application/json",
  }
})