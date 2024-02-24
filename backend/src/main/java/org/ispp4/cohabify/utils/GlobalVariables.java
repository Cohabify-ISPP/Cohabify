package org.ispp4.cohabify.utils;

import org.springframework.stereotype.Component;
import org.ispp4.cohabify.user.User;

@Component
public class GlobalVariables {
    
    private User loggedInUser;

    public User getLoggedInUser() {
        return loggedInUser;
    }

    public void setLoggedInUser(User loggedInUser) {
        this.loggedInUser = loggedInUser;
    }
}