import axios from "axios";

export const baseClient = axios.create({
  baseURL: "http://localhost:8080/",
  headers: {
    accept: "application/json",
  }
})