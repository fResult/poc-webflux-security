package com.fResult.poc_webflux_security

import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/rc")
class GreetingController {
  @GetMapping("/greetings")
  fun greet(@AuthenticationPrincipal user: Mono<UserDetails>) =
    user.map(UserDetails::getUsername).map { GreetingResponse("Hello, from Rest Controller, $it") }
}
