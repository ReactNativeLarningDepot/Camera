import {User} from "../app/models/User";
import {baseClient} from "./base-client";

export const getHoge = async (user: User) => {
  const { data } = await baseClient.get<GetHogeResponse>(
    '/api/hoge',
    { headers: { Authorization: `Bearer ${user.accessToken}`} }
  )
  return data
}

export type GetHogeResponse = {
  text: string
}