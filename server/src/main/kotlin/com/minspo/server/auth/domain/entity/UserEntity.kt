package com.minspo.server.auth.domain.entity

import jakarta.persistence.*

@Entity(name = "users")
data class UserEntity(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long = 0,
  @Column(unique = true)
  val oid: String = "",
  var name: String = "",
  var email: String = "",
)