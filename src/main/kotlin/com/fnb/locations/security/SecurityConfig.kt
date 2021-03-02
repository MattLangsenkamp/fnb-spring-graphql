package com.fnb.locations.security

import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.web.cors.CorsConfiguration

@EnableWebFluxSecurity
class SecurityConfig {

    @Bean
    fun springSecurityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain {


        http
                .csrf().disable().cors().configurationSource { CorsConfiguration().applyPermitDefaultValues() }.and()
                .httpBasic().disable()
                .formLogin().disable()
                .logout().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
        http.cors()
        return http.build()
    }
}