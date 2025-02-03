package com.minspo.server.auth.coder

import com.minspo.server.auth.domain.model.OriginalPrincipal
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class OriginalJwtEncoder(
    @Value("\${app.jwt.secret}")
    private val secret: String,
    @Value("\${app.jwt.expiration-time}")
    private val expirationTime: Long,
) {
    private val key = Keys.hmacShaKeyFor(secret.toByteArray())

    fun encode(
      id: Long,
      principal: OriginalPrincipal,
    ): String {
        val copiedPrincipal = principal.newInstanceWith(id)

        val issuedAt = System.currentTimeMillis()
        val expiration = issuedAt + expirationTime * SECONDS
        val claims =
            Jwts.claims()
                .id(UUID.randomUUID().toString())
                .subject(copiedPrincipal.oid)
                .expiration(Date(expiration))
                .issuer(ISSUER)
                .issuedAt(Date(issuedAt))
                .add(PRINCIPAL_KEY, copiedPrincipal)
                .build()

        return Jwts.builder()
            .claims(claims)
            .signWith(key)
            .compact()
    }

    companion object {
        private const val PRINCIPAL_KEY = "principal"
        private const val ISSUER = "https://rungram.io"
        private const val SECONDS = 1000
    }
}
