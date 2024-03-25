package org.ispp4.cohabify.configuration;

import org.ispp4.cohabify.authentication.GoogleAuthenticationToken;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof OAuth2AuthenticationToken)) {
            return null; 
        }

        OAuth2AuthenticationToken oauthToken = (OAuth2AuthenticationToken) authentication;
        UserDetails userDetails = loadUserByOAuth2Token(oauthToken);
        return new GoogleAuthenticationToken(userDetails, oauthToken.getAuthorities());
    }

    private UserDetails loadUserByOAuth2Token(OAuth2AuthenticationToken oauthToken) {
        String username = oauthToken.getPrincipal().getAttribute("username");
        if (username == null) {
            throw new UsernameNotFoundException("No se pudo obtener el username del usuario");
        }
        return userDetailsService.loadUserByUsername(username);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return OAuth2AuthenticationToken.class.isAssignableFrom(authentication);
    }
}