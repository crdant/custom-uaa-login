package io.pivotal.pa.newengland.demo.uaa.gateway

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.cloud.gateway.route.builder.filters
import org.springframework.cloud.gateway.route.builder.routes
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory
import org.springframework.core.io.ClassPathResource
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import java.net.URI
import java.util.*


@Configuration
class KotlinRoutes {
    @Autowired
    lateinit private var tokenRelay: TokenRelayGatewayFilterFactory

    @Value("\${uaa.baseUrl}")
    lateinit private var uaaBaseUrl : String

    @Value("\${uaa.clientId}")
    lateinit private var clientId: String

    @Value("\${uaa.clientSecret}")
    lateinit private var clientSecret: String

    @Bean("KotlinRoutes")
    fun routes(routeLocatorBuilder: RouteLocatorBuilder): RouteLocator =
            routeLocatorBuilder.routes {
                route {
                    path("/authorize")
                    uri( uaaBaseUrl)
                    // method(HttpMethod.POST)
                    filters {
                        setPath("/oauth/token")
                        addRequestHeader("Authorization",
                                "Basic " + Base64.getEncoder().encodeToString( (clientId + ":" + clientSecret).toByteArray(Charsets.UTF_8)))
                    }
                }
                route {
                    path("/")
                    uri("forward:/index.html")
                }
            }

    @Bean
    fun router() = org.springframework.web.reactive.function.server.router {
        accept(MediaType.TEXT_HTML).nest {
            GET("/login") {
                ok().render("password_form")
            }
        }
        resources("/**", ClassPathResource("static/"))
    }
}

