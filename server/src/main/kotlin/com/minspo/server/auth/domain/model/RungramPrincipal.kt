package com.minspo.server.auth.domain.model

import org.springframework.security.core.GrantedAuthority

data class RungramPrincipal(
  val id: Long = 0,
  val oid: String = "",
  val name: String = "",
  val email: String = "",
  val authorities: List<GrantedAuthority> = listOf()
) {
  fun newInstanceWith(id: Long): RungramPrincipal {
    return RungramPrincipal(
      id,
      this.oid,
      this.name,
      this.email,
      ArrayList(this.authorities)
    )
  }
}
