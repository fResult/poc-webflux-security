package com.fResult.poc_webflux_security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User
import org.springframework.security.web.server.SecurityWebFilterChain

@Configuration
open class SecurityConfiguration {
  /**
   * Creates a [MapReactiveUserDetailsService] with two in-memory users
   *
   * Passwords are stored in plain text for demonstration purposes.
   */
  @Bean
  open fun reactiveUserDetailsService(): MapReactiveUserDetailsService {
    val userPasswordPairs = listOf("fResult" to "fResult123", "KornZilla" to "KornZilla123")

    return userPasswordPairs.map(toUserBuilderWithRoles)
      .map(User.UserBuilder::build)
      .let { MapReactiveUserDetailsService(*it.toTypedArray()) }
  }

  @Bean
  open fun securityFilterChain(serverSecurity: ServerHttpSecurity): SecurityWebFilterChain =
    serverSecurity.httpBasic(Customizer.withDefaults())
      .authorizeExchange(::authorizeExchangeCustomizer)
      .csrf(ServerHttpSecurity.CsrfSpec::disable)
      .build()

  private val toUserBuilderWithRoles: (Pair<String, String>) -> User.UserBuilder = { (username, password) ->
    User.withUsername(username).password("{noop}$password").let(applyRolesToUserBuilder(username))
  }

  private fun applyRolesToUserBuilder(username: String): Identity<User.UserBuilder> = { userBuilder ->
    when (username) {
      "KornZilla" -> userBuilder.roles("USER", "ADMIN")
      "fResult" -> userBuilder.roles("USER")
      else -> userBuilder
    }
  }

  private fun authorizeExchangeCustomizer(
    exchangeSpec: ServerHttpSecurity.AuthorizeExchangeSpec,
  ): ServerHttpSecurity.AuthorizeExchangeSpec =
    exchangeSpec.pathMatchers("/rc/greetings**", "/fe/greetings**")
      .authenticated()
      .anyExchange()
      .permitAll()
}
