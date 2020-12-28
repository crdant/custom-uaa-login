package io.pivotal.pa.newengland.demo.uaa.gateway

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication
@EnableDiscoveryClient

class LoginGateway

fun main(args: Array<String>) {
	runApplication<LoginGateway>(*args)
}

