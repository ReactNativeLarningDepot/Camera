package com.minspo.server.auth.controller

import com.minspo.server.auth.coder.OriginalJwtEncoder
import com.minspo.server.auth.domain.model.AuthUserResponse
import com.minspo.server.auth.domain.model.OriginalPrincipal
import com.minspo.server.auth.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/api/users")
class AuthUserController(
  private val userService: UserService,
  private val jwtEncoder: OriginalJwtEncoder
) {
  @PostMapping("/me")
  fun saveUser(
    @AuthenticationPrincipal principal: OriginalPrincipal
  ): AuthUserResponse {
    val userResponse = userService.createOrGetUser(principal)
    return userResponse.with(jwtEncoder.encode(userResponse.id, principal))
  }
}
