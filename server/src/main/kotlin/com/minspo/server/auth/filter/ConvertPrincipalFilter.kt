package com.minspo.server.auth.filter

import com.minspo.server.auth.domain.model.RungramAuthenticationToken
import com.minspo.server.auth.domain.model.OriginalPrincipal
import com.minspo.server.auth.domain.model.UserRole
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter

class ConvertPrincipalFilter: OncePerRequestFilter() {
  private fun createRunGramAuthentication(principal: Jwt) {
    val oid = principal.getClaimAsString("oid")
    val name = principal.getClaimAsString("name")
    val email = principal.getClaimAsString("preferred_username")

    val authorities = listOf(SimpleGrantedAuthority(UserRole.ROLE_MEMBER.name))
    createNewContext(
      RungramAuthenticationToken(
        OriginalPrincipal(
          id = 0L,
          oid = oid,
          name = name,
          email = email,
          authorities = authorities,
        ),
        principal.tokenValue,
        authorities.toMutableList(),
      )
    )
  }

  private fun createNewContext(
    authentication: RungramAuthenticationToken,
  ) {
    authentication.isAuthenticated = true
    val context = SecurityContextHolder.createEmptyContext()
    context.authentication = authentication
    SecurityContextHolder.setContext(context)
  }

  override fun doFilterInternal(
    request: HttpServletRequest,
    response: HttpServletResponse,
    filterChain: FilterChain
  ) {
    val securityContext = SecurityContextHolder.getContext()
    if (securityContext == null || securityContext.authentication == null) {
      filterChain.doFilter(request, response)
      return
    }
    val authentication = securityContext.authentication
    if (authentication is JwtAuthenticationToken) {
      createRunGramAuthentication(authentication.principal as Jwt)
    }
    filterChain.doFilter(request, response)
  }
}
