package com.minspo.server.auth.controller

import com.minspo.server.auth.coder.RungramJwtEncoder
import com.minspo.server.auth.domain.model.AuthUserResponse
import com.minspo.server.auth.domain.model.RungramPrincipal
import com.minspo.server.auth.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth/api/users")
class AuthUserController(
  private val userService: UserService,
  private val jwtEncoder: RungramJwtEncoder
) {
  @PostMapping("/me")
  fun saveUser(
    @AuthenticationPrincipal principal: RungramPrincipal
  ): AuthUserResponse {
    val userResponse = userService.createOrGetUser(principal)
    return userResponse.with(jwtEncoder.encode(userResponse.id, principal))
  }
}
