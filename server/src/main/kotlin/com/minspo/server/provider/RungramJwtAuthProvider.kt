package com.minspo.server.provider

import com.minspo.server.auth.coder.OriginalJwtDecoder
import com.minspo.server.auth.domain.model.RungramAuthenticationToken
import com.minspo.server.auth.service.UserService
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.Authentication

class RungramJwtAuthProvider(
  private val jwtDecoder: OriginalJwtDecoder,
  private val userService: UserService,
) : AuthenticationProvider {
  override fun authenticate(authentication: Authentication?): Authentication {
    val runGramAuthenticationToken = authentication as RungramAuthenticationToken
    val accessToken = runGramAuthenticationToken.credentials as String
    try {
      val principal = jwtDecoder.decode(accessToken)
      if (userService.exists(principal.id)) {
        return RungramAuthenticationToken.createAuthenticatedToken(principal, accessToken)
      }
      throw RuntimeException()
    } catch (exception: RuntimeException) {
      throw BadCredentialsException("invalid access token", exception)
    }
  }

  override fun supports(authentication: Class<*>): Boolean {
    return RungramAuthenticationToken::class.java.isAssignableFrom(authentication)
  }
}