package com.minspo.server.app

import com.minspo.server.auth.domain.model.OriginalPrincipal
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/hoge")
class HogeController {
  @GetMapping
  fun getHoge(authentication: Authentication ): HogeResponse {
    val user = authentication.principal as OriginalPrincipal
    return HogeResponse("${user.name}さんのロールは${user.authorities.first()}です")
  }
}

data class HogeResponse(
  val text: String
)