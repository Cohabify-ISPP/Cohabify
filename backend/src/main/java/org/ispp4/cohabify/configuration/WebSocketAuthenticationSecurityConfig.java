package org.ispp4.cohabify.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import lombok.AllArgsConstructor;

@Configuration
@Order(Ordered.HIGHEST_PRECEDENCE + 99)
@AllArgsConstructor
public class WebSocketAuthenticationSecurityConfig implements WebSocketMessageBrokerConfigurer {

    private JwtChannelInterceptor jwtChannelInterceptor;
    
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        // Endpoints are already registered on WebSocketConfig, no need to add more.
    }

    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(jwtChannelInterceptor);
    } 

}
