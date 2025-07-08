package com.fResult.poc_webflux_security.fe

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

@Configuration
open class GreetingRouter(val greetingHandler: GreetingHandler) {
  @Bean
  open fun greetingRouts(): RouterFunction<ServerResponse> = coRouter {
    "/fe".nest {
      GET("/greetings", greetingHandler::greet)
    }
  }
}
