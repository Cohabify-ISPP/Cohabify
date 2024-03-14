package org.ispp4.cohabify.user;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class UserController {

    private UserService userService;

    @PostMapping("/add")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        try {
            if (user.getUsername() == null || user.getDescription() == null || user.getPhone() == null
                    || user.getEmail() == null || user.getPassword() == null || user.getPlan() == null
                    || user.getIsVerified() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            User newUser = userService.save(user);
            return new ResponseEntity<>(newUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/list")
    public ResponseEntity<List<User>> getAllUsers() {
        try {
            List<User> users = userService.findAll();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") ObjectId id) {
        Optional<User> userData = userService.findById(id);

        if (userData.isPresent()) {
            return new ResponseEntity<>(userData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/secureSearch")
    public ResponseEntity<User> secureSearch(@RequestBody String requestBody) {
        try {

            JsonNode jsonNode = new ObjectMapper().readTree(requestBody);
            String username = jsonNode.get("username").asText();
            String phone = jsonNode.get("phone").asText();
            User user = userService.getUserByUsernameAndPhone(username, phone);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/owners")
    public ResponseEntity<List<User>> findOwners() {
        try {
            List<User> users = userService.findByIsOwner(true);

            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<User> updateUser(@PathVariable("id") ObjectId id, @Valid @RequestBody User user) {
        Optional<User> userData = userService.findById(id);

        if (userData.isPresent()) {
            User _user = userData.get();
            _user.setUsername(user.getUsername());
            _user.setPassword(user.getPassword());
            _user.setIsOwner(user.getIsOwner());
            _user.setPhone(user.getPhone());
            _user.setEmail(user.getEmail());
            _user.setTag(user.getTag());
            _user.setDescription(user.getDescription());
            _user.setPlan(user.getPlan());
            _user.setIsVerified(user.getIsVerified());
            _user.setAuthorities(user.getAuthorities());
            _user.setLikes(user.getLikes());

            return new ResponseEntity<>(userService.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("update/plan/{plan}/{id}")
    public ResponseEntity<User> updateUserPlan(@PathVariable("id") ObjectId id,@PathVariable("plan") String plan) {
        Optional<User> userData = userService.findById(id);  
        if (userData.isPresent()) {
            User _user = userData.get();
            if(plan.equals("basic")){
                _user.setPlan(Plan.BASIC);
            }else if(plan.equals("explorer")){
                _user.setPlan(Plan.EXPLORER);
            }else if(plan.equals("owner")){
                _user.setPlan(Plan.OWNER);
            }else{
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            
            return new ResponseEntity<User>(userService.save(_user), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") ObjectId id) {
        try {
            userService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("like/{id}/{raterId}")
    public ResponseEntity<User> modifyRaters(@PathVariable("id") ObjectId id,
            @PathVariable("raterId") ObjectId raterId) {

        try {
            Optional<User> optionalUser = userService.findById(id);
            if (optionalUser.isPresent() == false) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                User user = optionalUser.get();
                Optional<User> optionalRaterUser = userService.findById(raterId);

                if (optionalRaterUser.isPresent() == false) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                } else {
                    User raterUser = optionalRaterUser.get();
                    List<User> positiveRaters = user.getLikes();

                    if (positiveRaters.contains(raterUser)) {
                        positiveRaters.remove(raterUser);
                    } else {
                        positiveRaters.add(raterUser);
                    }
                    user.setLikes(positiveRaters);
                    user = userService.save(user);
                    return new ResponseEntity<User>(user, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}