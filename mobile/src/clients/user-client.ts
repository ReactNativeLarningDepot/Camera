import {baseClient} from "./base-client";
import {GetUserResponse} from "../app/api/request/GetUserResponse";

export const getMe = async (idToken: string) => {
  const { data } = await baseClient.post<GetUserResponse>(
    '/auth/api/users/me',
    {},
    { headers: { Authorization: `Bearer ${idToken}`} }
  )
  return data
}

