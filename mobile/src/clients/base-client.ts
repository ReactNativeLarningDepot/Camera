import axios from "axios";
import {Platform} from "react-native";

const BASE_URL = Platform.OS === "ios" ? "http://localhost:8080/" : "http://10.0.2.2:8080/"

export const baseClient = axios.create({
  baseURL: BASE_URL,
  headers: {
    accept: "application/json",
  }
})