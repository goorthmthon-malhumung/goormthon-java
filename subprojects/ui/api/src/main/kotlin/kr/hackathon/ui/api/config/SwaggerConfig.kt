package kr.hackathon.ui.api.config

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.servers.Server
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@OpenAPIDefinition(
    servers = [
        Server(url = "https://junhugaeapi.goorm.training/", description = "https 서버입니다."),
        Server(url = "http://junhugaeapi.goorm.training/", description = "http 서버입니다."),
        Server(url = "http://localhost:8080", description = "kopis local 서버입니다.")
    ]
)
@Configuration
class SwaggerConfig {

    @Bean
    fun openAPI(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .version("v1.0.0")
                    .title("api")
                    .description("API 명세서")
            )
    }
}