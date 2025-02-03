package com.minspo.server.auth.coder

import com.minspo.server.auth.domain.model.OriginalPrincipal
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component

@Component
class OriginalJwtDecoder(
    @Value("\${app.jwt.secret}")
    private val secret: String,
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    @Throws(JwtException::class, IllegalArgumentException::class)
    @Suppress("UNCHECKED_CAST")
    fun decode(accessToken: String): OriginalPrincipal {
        val result =
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(accessToken)
        val principal = result.payload[PRINCIPAL_KEY] as Map<*, *>
        val authorities = principal["authorities"] as List<Map<*, *>>
        return OriginalPrincipal(
            principal["id"].toString().toLong(),
            principal["oid"].toString(),
            principal["name"].toString(),
            principal["email"].toString(),
            authorities.map { it["authority"].toString() }
                .map { SimpleGrantedAuthority(it) }
                .toList(),
        )
    }

    companion object {
        private const val PRINCIPAL_KEY = "principal"
    }
}
