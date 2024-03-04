package org.ispp4.cohabify.utils;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserRepository;

@Component
public class Global {

    private final UserRepository userRepository;

    public Global(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    
    @Transactional
    public User getCurrentUser() {
        
        User currentUser = null;
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            Optional<User> user = userRepository.findByUsername(userDetails.getUsername());
            if (user.isPresent()) {
                return user.get();
            }else{
                return null;
            }
        }
        return currentUser;
    }
}
