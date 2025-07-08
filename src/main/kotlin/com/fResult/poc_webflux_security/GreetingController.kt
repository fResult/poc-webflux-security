package com.fResult.poc_webflux_security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

data class GreetingResponse(val greetings: String)

@RestController
@RequestMapping("/greetings")
class GreetingController {
  @GetMapping
  fun greet(@AuthenticationPrincipal user: Mono<UserDetails>) =
    user.map(UserDetails::getUsername).map { GreetingResponse(it) }
}
