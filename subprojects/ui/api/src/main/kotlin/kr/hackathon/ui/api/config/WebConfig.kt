package kr.hackathon.ui.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebConfig : WebMvcConfigurer {
    companion object {
        const val FRONT_URL = "https://junhugae.goorm.training"
    }
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val photoAbsPath = Paths.get("photo").toAbsolutePath().toString()
        val mediaAbsPath = Paths.get("media").toAbsolutePath().toString()
        registry.addResourceHandler("/photo/**")
            .addResourceLocations("file:$photoAbsPath/")

        registry.addResourceHandler("/media/**")
            .addResourceLocations("file:$mediaAbsPath/")
    }

    override fun addCorsMappings(registry: CorsRegistry) {
        registry.addMapping("/**")
            .allowedOrigins(FRONT_URL, "localhost:5173")
            .allowedMethods("GET", "POST", "PUT", "DELETE")
            .allowedHeaders("*")
            .allowCredentials(true)
    }
}