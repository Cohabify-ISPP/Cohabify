package org.ispp4.cohabify.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/chat");
		config.setApplicationDestinationPrefixes("/chat-msgs");
		
	}

	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/chat-ws")
				.setAllowedOrigins("http://localhost:5173", "https://localhost:5173", "ws://localhost:5173",
									"http://s3-cohabify.onrender.com", "https://s3-cohabify.onrender.com", "ws://s3-cohabify.onrender.com")
				.withSockJS();
	}

	

}
