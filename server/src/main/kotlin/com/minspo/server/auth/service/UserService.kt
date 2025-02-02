package com.minspo.server.auth.service

import com.minspo.server.auth.domain.entity.UserEntity
import com.minspo.server.auth.domain.model.AuthUserResponse
import com.minspo.server.auth.domain.model.RungramPrincipal
import com.minspo.server.auth.repository.UserRepository
import org.springframework.stereotype.Service

interface UserService {
  fun createOrGetUser(principal: RungramPrincipal): AuthUserResponse
}

@Service
class UserServiceImpl(
  private val userRepository: UserRepository,
): UserService {
  override fun createOrGetUser(principal: RungramPrincipal): AuthUserResponse {
    val userEntity = userRepository.findByOid(principal.oid)
      ?: userRepository.save(
        UserEntity(
          oid = principal.oid,
          name = principal.name,
          email = principal.email,
        )
      )

    return AuthUserResponse.from(userEntity)
  }
}