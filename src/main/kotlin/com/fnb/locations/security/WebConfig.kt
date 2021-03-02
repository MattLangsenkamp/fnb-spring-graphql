package com.fnb.locations.security

import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.config.CorsRegistry
import org.springframework.web.reactive.config.EnableWebFlux
import org.springframework.web.reactive.config.WebFluxConfigurer

@Configuration
@EnableWebFlux
class WebConfig: WebFluxConfigurer
{
    override fun addCorsMappings(registry: CorsRegistry)
    {
        registry.addMapping("/**")
            .allowedOriginPatterns("*")// any host or put domain(s) here
            .allowedMethods("*") // put the http verbs you want allow
            .allowedHeaders("Access-Control-Allow-Headers",
                    "Access-Control-Allow-Origin",
                    "AccessToken",
                    "RefreshToken") // put the http headers you want allow

    }
}