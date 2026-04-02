package kr.hackathon.ui.api.config

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.nio.file.Paths

@Configuration
class WebConfig : WebMvcConfigurer {
    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        val photoAbsPath = Paths.get("photo").toAbsolutePath().toString()
        val mediaAbsPath = Paths.get("media").toAbsolutePath().toString()
        registry.addResourceHandler("/photo/**")
            .addResourceLocations("file:$photoAbsPath/")

        registry.addResourceHandler("/media/**")
            .addResourceLocations("file:$mediaAbsPath/")
    }
}