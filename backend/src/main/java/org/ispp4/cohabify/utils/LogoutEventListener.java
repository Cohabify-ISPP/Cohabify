package org.ispp4.cohabify.utils;

import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.LogoutSuccessEvent;

import org.springframework.stereotype.Component;

@Component
public class LogoutEventListener implements ApplicationListener<LogoutSuccessEvent> {

    private final GlobalVariables globalVariables;

    public LogoutEventListener(GlobalVariables globalVariables) {
        this.globalVariables = globalVariables;
    }

    @Override
    public void onApplicationEvent(LogoutSuccessEvent event) {
        globalVariables.setLoggedInUser(null);
    }   
}