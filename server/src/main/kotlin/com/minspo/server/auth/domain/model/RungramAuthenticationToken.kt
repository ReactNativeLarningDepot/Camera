package com.minspo.server.auth.domain.model

import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority

data class RungramAuthenticationToken(
  private val principal: RungramPrincipal,
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
}
