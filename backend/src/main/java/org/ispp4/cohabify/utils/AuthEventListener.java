package org.ispp4.cohabify.utils;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.ispp4.cohabify.user.UserRepository;
import org.ispp4.cohabify.user.User;

@Component
public class AuthEventListener implements ApplicationListener<AuthenticationSuccessEvent> {

    private final GlobalVariables globalVariables;
    
    @Autowired
    private UserRepository userRepository;

    public AuthEventListener(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @Override
    public void onApplicationEvent(AuthenticationSuccessEvent event) {
        User loggedInUsername = userRepository.findByUsername(event.getAuthentication().getName()).get();
        globalVariables.setLoggedInUser(loggedInUsername);
    }
}