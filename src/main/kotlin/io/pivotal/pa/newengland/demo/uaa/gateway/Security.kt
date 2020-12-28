package io.pivotal.pa.newengland.demo.uaa.gateway

import org.springframework.context.annotation.Bean
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity

@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    fun oauth2Security(http: ServerHttpSecurity): SecurityWebFilterChain {
        http
                .csrf().disable()
                .authorizeExchange().pathMatchers("/", "/**/*.html", "/*.ico", "/js/**", "/css/**", "/img/**").permitAll()
                .and()
                .authorizeExchange().pathMatchers("/password", "/auth/**", "/actuator/**").permitAll()
                .anyExchange().authenticated()
                .and()
                .oauth2Login()
        return http.build()
    }
}
