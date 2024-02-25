package org.ispp4.cohabify.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("http://localhost:8080", "ws://localhost:8080",
								"http://localhost:5173", "ws://localhost:5173")
				.allowedMethods("*") 
				.allowedHeaders("Authentication", "content-type")
				.allowCredentials(true)
				.maxAge(3600);
	}
	
}
