package com.fResult.poc_webflux_security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService
import org.springframework.security.core.userdetails.User

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

    return userPasswordPairs.map(toUserBuilder)
      .mapIndexed(::toAppliedRoles)
      .map(User.UserBuilder::build)
      .let { MapReactiveUserDetailsService(*it.toTypedArray()) }
  }

  private val toUserBuilder: (Pair<String, String>) -> User.UserBuilder = { (username, password) ->
    User.withUsername(username).password("{noop}$password")
  }

  private fun toAppliedRoles(idx: Int, userBuilder: User.UserBuilder) = when (idx) {
    0 -> userBuilder.roles("USER")
    1 -> userBuilder.roles("USER", "ADMIN")
    else -> userBuilder
  }
}
