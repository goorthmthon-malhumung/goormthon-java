package kr.hackathon.ui.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Configuration
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackages = ["kr.hackathon"])
@Configuration
@EntityScan(basePackages = ["kr.hackathon.domain"])
@EnableJpaRepositories(basePackages = ["kr.hackathon.domain"])
open class HackathonApiApplication

fun main(args: Array<String>) {
    runApplication<HackathonApiApplication>(*args)
}
