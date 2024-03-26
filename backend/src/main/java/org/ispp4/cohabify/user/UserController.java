package org.ispp4.cohabify.user;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.lang.Nullable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.ispp4.cohabify.dto.UserUpdateRequest;
import org.ispp4.cohabify.storage.StorageService;
import org.ispp4.cohabify.authentication.JwtService;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.JwtTokenDto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
@Validated
public class UserController {

    private UserService userService;
    private JwtService jwtService;
    private PasswordEncoder passwordEncoder;
    private StorageService storageService;

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

    @Transactional
    @PutMapping("update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") ObjectId id, @Valid @RequestPart("string-data") UserUpdateRequest request, 
                                        BindingResult result, @Nullable @RequestPart("profile-pic") MultipartFile image) throws IOException {
        if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(result.getFieldErrors()
										 	 .stream()
										 	 	.map(fe -> new FormItemValidationError(fe))
										 	 	.toList());
		}

        User _user = userService.getUserById(id);

        if (_user != null) {
            User user = userService.getUserByUsername(request.getUsername());
            if(user != null && !user.getId().equals(_user.getId())) {
                FormItemValidationError error = new FormItemValidationError();
                error.setField("username");
                error.setCode("AlreadyExists");
                error.setMessage("An user with this username already exists.");
                error.setRejectedValue(request.getUsername());
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(error);
            }

            _user.setUsername(request.getUsername());
            _user.setPhone(request.getPhone());
            _user.setEmail(request.getEmail());
            _user.setGender(request.getGender());
            _user.setTag(request.getTag());
            _user.setDescription(request.getDescription());

            if(request.getPassword() != null && !request.getPassword().isBlank()) {
                _user.setPassword(passwordEncoder.encode(request.getPassword()));
            } 

            if(request.getChangedImage()) {
                // Save the image and add the static uri to the user
                String[] filename_split = image.getOriginalFilename().split("\\.");
                String filename = _user.getJsonId() + "." + filename_split[filename_split.length-1];
                String static_path;
                static_path = storageService.saveImage(filename, image); 
                
                _user.setImageUri(static_path);
                _user = userService.save(_user);
            }

            String jwt = jwtService.generateToken(_user);

            return ResponseEntity.status(HttpStatus.OK)
							 .body(JwtTokenDto.builder().user(userService.save(_user)).token(jwt).build());
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }

    @PutMapping("update/plan/{plan}/{id}")
    public ResponseEntity<User> updateUserPlan(@PathVariable("id") ObjectId id, @PathVariable("plan") String plan) {
        Optional<User> userData = userService.findById(id);
        if (userData.isPresent()) {
            User _user = userData.get();
            if (plan.equals("basic")) {
                _user.setPlan(Plan.BASIC);
            } else if (plan.equals("explorer")) {
                _user.setPlan(Plan.EXPLORER);
                LocalDate expirationDate = LocalDate.now().plusMonths(1);
                _user.setExpirationDate(expirationDate);
            } else if (plan.equals("owner")) {
                _user.setPlan(Plan.OWNER);
                LocalDate expirationDate = LocalDate.now().plusMonths(1);
                _user.setExpirationDate(expirationDate);
            } else {
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
                    Optional<User> foundUser = positiveRaters.stream().filter(x-> x.getId().equals(raterId)).findFirst();
                    if (foundUser.isPresent()) {
                        positiveRaters.remove(foundUser.get());
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