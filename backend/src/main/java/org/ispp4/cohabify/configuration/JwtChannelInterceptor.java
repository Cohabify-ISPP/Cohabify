package org.ispp4.cohabify.configuration;

import org.ispp4.cohabify.authentication.JwtService;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class JwtChannelInterceptor implements ChannelInterceptor {

    private JwtService jwtService;
    private CustomUserDetailsService userDetailsService;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = accessor.getFirstNativeHeader("Authentication");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7);
                String username = jwtService.extractUserName(token);
                UserDetails user = userDetailsService.loadUserByUsername(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                accessor.setUser(authentication);
            }
        }
        return message;
    }
}

