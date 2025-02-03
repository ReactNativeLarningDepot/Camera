package com.minspo.server.auth.service

import com.minspo.server.auth.domain.entity.UserEntity
import com.minspo.server.auth.domain.model.AuthUserResponse
import com.minspo.server.auth.domain.model.OriginalPrincipal
import com.minspo.server.auth.repository.UserRepository
import org.springframework.stereotype.Service

interface UserService {
  fun createOrGetUser(principal: OriginalPrincipal): AuthUserResponse
  fun exists(id: Long): Boolean
}

@Service
class UserServiceImpl(
  private val userRepository: UserRepository,
): UserService {
  override fun createOrGetUser(principal: OriginalPrincipal): AuthUserResponse {
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

  override fun exists(id: Long): Boolean {
    return userRepository.existsById(id)
  }
}