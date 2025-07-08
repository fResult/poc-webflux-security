package com.fResult.poc_webflux_security

import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class GreetingRouters {
  @Bean
  open fun greetingRouts(): RouterFunction<ServerResponse> = coRouter {
    "/fe".nest {
      GET("/greetings") { request ->
        request.principal().map { it.name }
          .map { GreetingResponse("Hello from Functional Endpoint, $it!") }
          .flatMap { ServerResponse.ok().bodyValue(it) }
          .awaitSingle()
      }
    }
  }
}