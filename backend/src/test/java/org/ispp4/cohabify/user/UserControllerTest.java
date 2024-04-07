package org.ispp4.cohabify.user;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.JwtTokenDto;
import org.ispp4.cohabify.dto.UserUpdateRequest;
import org.ispp4.cohabify.tag.Tag;
import org.ispp4.cohabify.tag.TagService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;

import jakarta.validation.ConstraintViolationException;


@SpringBootTest
@TestInstance(Lifecycle.PER_CLASS)
public class UserControllerTest {
    
    @Autowired
    UserService userService;

    @Autowired
    UserController userController;

    @Autowired
    TagService tagService;

    User testUser;
    
    @BeforeAll
    void setUp(){
        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setName("name");
        user.setUsername("test");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666666");
        user.setEmail("test@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);

        testUser = userController.createUser(user).getBody();
    }

    @AfterAll
    void tearDown(){
        userService.deleteById(testUser.getId());
    }

    @Test
    void shouldCreateUser(){

        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setName("name77");
        user.setUsername("test77");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666677");
        user.setEmail("test77@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);

        ResponseEntity<User> response = userController.createUser(user);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        userService.deleteById(response.getBody().getId());
    }

    @Test
    void shouldNotCreateUser(){
        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setUsername("test000000000000000000000000000000000000000000000000000000");
        user.setPassword("test");
        user.setIsOwner(true);
        user.setPhone("test");
        user.setEmail("test@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);    

        try{
            userController.createUser(user);
            userService.deleteById(user.getId());
        }catch(Exception e){
            assertEquals(e.getClass(), 
            ConstraintViolationException.class);
        }
    }

    @Test
    void shouldListAllUsers(){
        ResponseEntity<List<User>> response = userController.getAllUsers();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assert(response.getBody().size() >= 1);
    }

    @Test
    void shouldFindById(){
        ResponseEntity<User> response = userController.getUserById(testUser.getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(testUser.getId(), response.getBody().getId());

        response = userController.getUserById(new ObjectId("69d5ec9af682fbd39a1b8b9d"));
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldFindBySecureSearch(){

        String requestBody = "{\"username\":\""+testUser.getUsername()+"\",\"phone\":\""+testUser.getPhone()+"\"}";

        ResponseEntity<User> response = userController.secureSearch(requestBody);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(testUser.getId(), response.getBody().getId());

        requestBody = "{\"username\":\"test00000000\",\"phone\":\"667966666\"}";
        response = userController.secureSearch(requestBody);
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldGetOwners(){
        ResponseEntity<List<User>> response = userController.findOwners();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assert(response.getBody().size()>=1);
        List<User> owners = response.getBody();
        for(User u : owners){
            u.setIsOwner(false);
            userService.save(u);
        }
        response = userController.findOwners();
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);

        for(User u : owners){
            u.setIsOwner(true);
            userService.save(u);
        }
    }

    @Test
    void shoudlUpdateUser(){

        List<Tag> tags = new ArrayList<>();
        tags.add(tagService.findAll().get(0));
        UserUpdateRequest request = new UserUpdateRequest("x","test2","Test_1512-ao",tags,testUser.getDescription(),testUser.getEmail(),"666666666",testUser.getGender(),false);
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        try {
            ResponseEntity<?> response = userController.updateUser(testUser.getId(), request, bindingResult, null);
            assertEquals(response.getStatusCode(), HttpStatus.OK);
            assert(response.getBody().getClass().equals(JwtTokenDto.class));
        } catch (IOException e) {   
            e.printStackTrace();
        }
        request = new UserUpdateRequest("name","test","Test_1512*123oa",tags,testUser.getDescription(),testUser.getEmail(),"666666666",testUser.getGender(),false);
        bindingResult = new BeanPropertyBindingResult(request, "request");
        try {
        userController.updateUser(testUser.getId(), request, bindingResult, null);
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }

    @Test
    void shouldNotUpdateUserConflictUsername(){
        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setUsername("test67");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666667");
        user.setEmail("test67@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);
        userService.save(user);

        List<Tag> tags = new ArrayList<>();
        tags.add(tagService.findAll().get(0));
        UserUpdateRequest request = new UserUpdateRequest("x","manlovper90","Test_1512-ao",tags,user.getDescription(),user.getEmail(),"666666666",user.getGender(),false);
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        try {
            ResponseEntity<?> response = userController.updateUser(user.getId(), request, bindingResult, null);
            assertEquals(response.getStatusCode(), HttpStatus.CONFLICT);
        } catch (IOException e) {   
            e.printStackTrace();
        }
        userService.deleteById(user.getId());
    }

    @Test
    void shouldNotUpdateUserInvalid(){
        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setUsername("test99");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666699");
        user.setEmail("test99@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);
        userService.save(user);

        try {
            List<Tag> tags = new ArrayList<>();
            tags.add(tagService.findAll().get(0));
            UserUpdateRequest request = new UserUpdateRequest("x","test","",tags,user.getDescription(),user.getEmail(),"test",user.getGender(),false);
            BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
            userController.updateUser(user.getId(), request, bindingResult, null);
        } catch (Exception e) {   
            assertEquals(e.getClass(), ConstraintViolationException.class);
        }
        userService.deleteById(user.getId());
    }

    @Test
    void shouldNotFindUserToUpdate(){
        List<String> authorities = new ArrayList<>();
        authorities.add("User");
        User user = new User();
        user.setUsername("test");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666666");
        user.setEmail("test@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);
        user.setId( new ObjectId("69d5ec9af682fbd39a1b8b9d"));

        List<Tag> tags = new ArrayList<>();
        tags.add(tagService.findAll().get(0));
        UserUpdateRequest request = new UserUpdateRequest("x","test2","Test_1512-ao",tags,user.getDescription(),user.getEmail(),"666666666",user.getGender(),false);
        BindingResult bindingResult = new BeanPropertyBindingResult(request, "request");
        try {
            ResponseEntity<?> response = userController.updateUser(user.getId(), request, bindingResult, null);
            assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        } catch (IOException e) {   
            e.printStackTrace();
        }
    }

    @Test
    void shouldUpdatePlan(){ 
        
        ResponseEntity<User> response = userController.updateUserPlan(testUser.getId(), "basic");
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(testUser.getId(), response.getBody().getId());
    }

    @Test
    void shouldNotUpdatePlan(){

        ResponseEntity<User> response = userController.updateUserPlan(testUser.getId(), "error");
        assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
        
        User user = new User();
        userService.save(user);
        ObjectId id= user.getId();
        userService.deleteById(id);
        response = userController.updateUserPlan(id, "basic");
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    void shouldDeleteUser(){
        List<String> authorities = new ArrayList<>();
        User user = new User();
        user.setName("name23");
        user.setUsername("test23");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666667");
        user.setEmail("test23@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);
        userService.save(user);
        user = userController.createUser(user).getBody();
        ResponseEntity<HttpStatus> response = userController.deleteUser(user.getId());
        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @Test
    @WithMockUser(username = "test", password = "Test_1512*123oa", authorities = {"User"})
    void shouldToggleLike(){
        List<String> authorities = new ArrayList<>();
        User user = new User();
        user.setUsername("test69");
        user.setPassword("Test_1512*123oa");
        user.setIsOwner(true);
        user.setPhone("666666669");
        user.setEmail("test69@gmail.com");
        user.setDescription("test description");
        user.setPlan(Plan.BASIC);
        user.setGender(Gender.FEMENINO);
        user.setIsVerified(false);
        user.setAuthorities(authorities);
        userService.save(user);

        ResponseEntity<User> response = userController.toggleLike(user.getId());
        assertEquals(response.getStatusCode(), HttpStatus.OK);

        userService.deleteById(user.getId());
        response = userController.toggleLike(user.getId());
        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
    }
}
