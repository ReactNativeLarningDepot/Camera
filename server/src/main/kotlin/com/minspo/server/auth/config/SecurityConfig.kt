package com.minspo.server.auth.config

import com.minspo.server.auth.filter.ConvertPrincipalFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
  @Bean
  fun authSecurityFilterChain(http: HttpSecurity): SecurityFilterChain {
    http.securityMatcher("/auth/api/users/me")
      .csrf { it.ignoringRequestMatchers("/auth/**") }
      .authorizeHttpRequests {
        it.anyRequest().authenticated()
      }
      .oauth2ResourceServer {
        it.jwt {}
      }
      .addFilterBefore(
        ConvertPrincipalFilter(),
        AnonymousAuthenticationFilter::class.java
      )

    return http.build()
  }
}