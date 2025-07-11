package com.fResult.poc_webflux_security.fe

import com.fResult.poc_webflux_security.GreetingResponse
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import java.security.Principal

@Component
class GreetingHandler {
  suspend fun greet(request: ServerRequest): ServerResponse =
    request.principal().map(Principal::getName)
      .map { GreetingResponse("Hello from Functional Endpoint, $it!") }
      .flatMap { ServerResponse.ok().bodyValue(it) }
      .awaitSingle()

  suspend fun greetWithRoles(request: ServerRequest): ServerResponse =
    request.principal().cast(Authentication::class.java)
      .map(::toAuthorities)
      .map { GreetingResponse("Hello from Functional Endpoint with roles, $it!") }
      .flatMap { ServerResponse.ok().bodyValue(it) }
      .awaitSingle()

  private fun toAuthorities(auth: Authentication): List<String> {
    return auth.authorities.map(GrantedAuthority::getAuthority)
  }
}
