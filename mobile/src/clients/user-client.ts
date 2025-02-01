import {baseClient} from "./base-client";

export const getMe = async (idToken: string) => {
  const { data } = await baseClient.post<UserResponse>(
    '/auth/api/users/me',
    {},
    { headers: { Authorization: `Bearer ${idToken}`} }
  )
  return data
}

export type UserResponse = {
  id: number,
  name: string,
  accessToken: string
}