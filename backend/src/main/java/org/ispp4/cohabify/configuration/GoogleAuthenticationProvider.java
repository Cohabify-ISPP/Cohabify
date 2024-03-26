package org.ispp4.cohabify.configuration;

import org.ispp4.cohabify.authentication.GoogleAuthenticationToken;
import org.ispp4.cohabify.user.CustomUserDetailsService;
import org.ispp4.cohabify.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class GoogleAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${google.public.key}")
	private String googlePublicKey;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        if (!(authentication instanceof GoogleAuthenticationToken)) {
            return null; 
        }

        UserDetails userDetails = userDetailsService.loadUserByGoogleToken(authentication.getPrincipal().toString());
        return new GoogleAuthenticationToken(userDetails);
    }
    
    @Override
    public boolean supports(Class<?> authentication) {
        return GoogleAuthenticationToken.class.isAssignableFrom(authentication);
    }
}