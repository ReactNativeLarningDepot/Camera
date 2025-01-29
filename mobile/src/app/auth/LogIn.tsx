import { useState } from 'react';
import * as WebBrowser from 'expo-web-browser';
import {
  exchangeCodeAsync,
  makeRedirectUri,
  useAuthRequest,
  useAutoDiscovery,
} from 'expo-auth-session';
import { Button, Text, SafeAreaView } from 'react-native';


WebBrowser.maybeCompleteAuthSession();

const TENANT_ID = "b96130b7-e3bf-4026-ab41-4088ef571d95";
const CLIENT_ID = "cb29686b-b367-4718-b140-482831208a0a";

export default function LogIn() {
  const discovery = useAutoDiscovery(
    `https://login.microsoftonline.com/${TENANT_ID}/v2.0`,
  );
  const redirectUri = makeRedirectUri();
  const clientId = CLIENT_ID;
  const [token, setToken] = useState<string | null>(null);
  const [request, , promptAsync] = useAuthRequest(
    {
      clientId,
      scopes: ['openid', 'profile', 'email', 'offline_access'],
      redirectUri: redirectUri,
    },
    discovery,
  );

  const handleOnPressLogInButton = () => {
    promptAsync().then((codeResponse) => {
      if (request && codeResponse?.type === 'success' && discovery) {
        exchangeCodeAsync(
          {
            clientId,
            code: codeResponse.params.code,
            extraParams: request.codeVerifier
              ? { code_verifier: request.codeVerifier }
              : undefined,
            redirectUri: redirectUri,
          },
          discovery,
        ).then((res) => {
          setToken(res.accessToken);
          console.log(res.accessToken)
        });
      }
    });
  }

  return (
    <SafeAreaView>
      <Button
        disabled={!request}
        title="Login"
        onPress={handleOnPressLogInButton}
      />
      <Text>{token}</Text>
    </SafeAreaView>
  );
}
