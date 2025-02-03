package com.minspo.server.auth.config

import com.minspo.server.auth.coder.OriginalJwtDecoder
import com.minspo.server.auth.filter.ConvertPrincipalFilter
import com.minspo.server.auth.filter.OriginalJwtAuthenticationFilter
import com.minspo.server.auth.service.UserService
import com.minspo.server.provider.RungramJwtAuthProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.AnonymousAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig {
  @Bean
  fun runGramJwtAuthProvider(
    runGramJwtDecoder: OriginalJwtDecoder,
    userService: UserService,
  ): AuthenticationProvider {
    return RungramJwtAuthProvider(runGramJwtDecoder, userService)
  }

  @Bean
  fun authenticationManager(authenticationProviders: List<AuthenticationProvider>): AuthenticationManager {
    return ProviderManager(authenticationProviders)
  }

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

  @Bean
  fun appSecurityFilterChain(
    http: HttpSecurity,
    authenticationManager: AuthenticationManager,
  ): SecurityFilterChain {
    http.securityMatcher("/api/**", "/h2-console/**")
      .csrf {
        it.ignoringRequestMatchers("/api/**", "/h2-console/**")
      }
      .authorizeHttpRequests {
       it.anyRequest().authenticated()
      }
      .addFilterBefore(
        OriginalJwtAuthenticationFilter(authenticationManager),
        AnonymousAuthenticationFilter::class.java
      )

    return http.build()
  }
}