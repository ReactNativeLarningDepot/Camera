import * as WebBrowser from 'expo-web-browser';
import {
  AuthSessionResult,
  exchangeCodeAsync,
  makeRedirectUri,
  TokenResponse,
  useAuthRequest,
  useAutoDiscovery,
} from 'expo-auth-session';
import {Alert, Button, SafeAreaView} from 'react-native';
import {getMe} from "../../clients/user-client";
import {User} from "../models/User";
import {GetUserResponse} from "../api/request/GetUserResponse";
import {useUserContext} from "../UserContext";


WebBrowser.maybeCompleteAuthSession()

export default function LogIn() {
  const tenant_id = "b96130b7-e3bf-4026-ab41-4088ef571d95"
  const clientId = "cb29686b-b367-4718-b140-482831208a0a"
  const redirectUri = makeRedirectUri()
  const discovery = useAutoDiscovery(
    `https://login.microsoftonline.com/${tenant_id}/v2.0`,
  )

  const [request, , promptAsync] = useAuthRequest(
    {
      clientId,
      scopes: ['openid', 'profile', 'email'],
      redirectUri: redirectUri,
    },
    discovery,
  )

  const { setUser } = useUserContext()

  const handleOnPressLogInButton = async () => {
    const codeResponse: AuthSessionResult = await promptAsync()

    if (request && codeResponse?.type === 'success' && discovery) {
      const idToken: string | undefined = await exChangeCodeToIdToken(codeResponse)

      if (idToken) {
        const userResponse: GetUserResponse = await getMe(idToken)
        const user: User = {
          id: userResponse.id,
          name: userResponse.name,
          accessToken: userResponse.accessToken
        }
        setUser(user)
      } else {
        Alert.alert("アクセストークンが取得できませんでした")
      }
    }
  }

  const exChangeCodeToIdToken = async (codeResponse: AuthSessionResult): Promise<string | undefined> => {
    if (!(request && codeResponse?.type === 'success' && discovery)) return undefined

    const res: TokenResponse =  await exchangeCodeAsync(
      {
        clientId,
        code: codeResponse.params.code,
        extraParams: request.codeVerifier
          ? { code_verifier: request.codeVerifier }
          : undefined,
        redirectUri: redirectUri,
      },
      discovery,
    )
    return res.idToken
  }

  return (
    <SafeAreaView>
      <Button
        disabled={!request}
        title="Login"
        onPress={handleOnPressLogInButton}
      />
    </SafeAreaView>
  )
}
