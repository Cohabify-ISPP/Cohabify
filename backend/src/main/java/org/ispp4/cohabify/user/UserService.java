package org.ispp4.cohabify.user;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;



@Service
@AllArgsConstructor
public class UserService {
  
    private final UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id) {
        return userRepository.findById(id);
    }

    public User save(User user) {
        return (User) userRepository.save(user);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public User getUserById(ObjectId id) {
        return userRepository.findById(id).orElse(null);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public User getUserByUsernameAndPhone(String username, String phone) {
        return userRepository.findByUsernameAndPhone(username,phone).orElse(null);
    }

	public List<User> findByIsOwner(Boolean owner) {
	  return userRepository.findByIsOwner(owner);
	}

    public Optional<User> getUserByGoogleOAuthToken(String googleAuthToken) {
        return userRepository.findBygoogleOAuthToken(googleAuthToken);
    }

    public Boolean isUserPlanValid(User user){
        LocalDate currentDate = LocalDate.now();
        if (user.getExpirationDate() == null)
            return false;
        return currentDate.isBefore(user.getExpirationDate());
    }

    public User findByVerificationCode(String verificationCode) {
        return userRepository.findByVerificationCode(verificationCode).orElse(null);
    }

}
