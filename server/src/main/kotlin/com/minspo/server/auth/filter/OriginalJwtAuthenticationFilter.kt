package com.minspo.server.auth.filter

import com.minspo.server.auth.domain.model.RungramAuthenticationToken
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.server.resource.web.DefaultBearerTokenResolver
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter

class OriginalJwtAuthenticationFilter(
  private val authenticationManager: AuthenticationManager,
): OncePerRequestFilter() {
  private val bearerTokenResolver = DefaultBearerTokenResolver()

  private fun createContext(authentication: Authentication?) {
    val context = SecurityContextHolder.createEmptyContext()
    context.authentication = authentication
    SecurityContextHolder.setContext(context)
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val accessToken = bearerTokenResolver.resolve(request)

    if (StringUtils.hasText(accessToken)) {
      try {
        val unauthenticatedToken = RungramAuthenticationToken.createToken(accessToken)
        val authentication = authenticationManager.authenticate(unauthenticatedToken)
        createContext(authentication)
      } catch(exception: AuthenticationException) {
        SecurityContextHolder.clearContext()
        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Forbidden")
        return
      }
    }
    filterChain.doFilter(request, response)
  }
}