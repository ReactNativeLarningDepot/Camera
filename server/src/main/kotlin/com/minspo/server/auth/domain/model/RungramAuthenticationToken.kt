package com.minspo.server.auth.domain.model

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

data class RungramAuthenticationToken(
  private val principal: OriginalPrincipal,
  private val credentials: String,
  private val authorities: MutableList<GrantedAuthority>
): Authentication {
  private var isAuthenticated: Boolean = false

  override fun getName(): String = principal.id.toString()

  override fun getAuthorities(): MutableCollection<out GrantedAuthority> = authorities

  override fun getCredentials(): Any = credentials

  override fun getDetails(): Any = ""

  override fun getPrincipal(): Any = principal

  override fun isAuthenticated(): Boolean = isAuthenticated

  override fun setAuthenticated(isAuthenticated: Boolean) {
    this.isAuthenticated = isAuthenticated
  }

  companion object {
    fun createToken(credentials: String): RungramAuthenticationToken {
      return RungramAuthenticationToken(
        OriginalPrincipal(),
        credentials,
        mutableListOf()
      )
    }

    fun createAuthenticatedToken(
      principal: OriginalPrincipal,
      credentials: String,
    ): RungramAuthenticationToken {
      val token =
        RungramAuthenticationToken(
          principal,
          credentials,
          principal.authorities.toMutableList(),
        )
      token.isAuthenticated = true
      return token
    }
  }
}
