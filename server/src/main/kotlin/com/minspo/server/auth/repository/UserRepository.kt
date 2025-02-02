package com.minspo.server.auth.repository

import com.minspo.server.auth.domain.entity.UserEntity
import org.springframework.data.repository.CrudRepository

interface UserRepository: CrudRepository<UserEntity, Long> {
  fun findByOid(oid: String): UserEntity?
}