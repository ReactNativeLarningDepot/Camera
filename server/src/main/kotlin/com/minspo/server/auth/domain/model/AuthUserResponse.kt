package com.minspo.server.auth.domain.model

import com.minspo.server.auth.domain.entity.UserEntity

data class AuthUserResponse(
  val id: Long,
  val name: String,
  val accessToken: String
) {
  fun with(accessToken: String): AuthUserResponse {
    return AuthUserResponse(this.id, this.name, accessToken)
  }

  companion object {
    fun from(
      user: UserEntity,
      accessToken: String = ""
    ): AuthUserResponse {
      return AuthUserResponse(user.id, user.name, accessToken)
    }
  }
}
