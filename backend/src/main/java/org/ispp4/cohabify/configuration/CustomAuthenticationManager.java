package org.ispp4.cohabify.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.stereotype.Component;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import java.util.List;
import java.nio.file.ProviderNotFoundException;

@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    @Autowired
    private List<AuthenticationProvider> authenticationProviders;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        for (AuthenticationProvider provider : authenticationProviders) {
            if (provider.supports(authentication.getClass())) {
                return provider.authenticate(authentication);
            }
        }
        throw new ProviderNotFoundException("No AuthenticationProvider found for the given Authentication");
    }
}