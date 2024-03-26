package org.ispp4.cohabify.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    
    @Autowired
    UserService userService;

    @Test
    void shouldFindAllUsers(){
        List<User> users = userService.findAll();
        assertTrue(users.size()>=1);
    }

    @Test
    void shouldFindUserById(){
        User user = userService.findById(new ObjectId("60d5ec9af682fbd39a1b8b9d")).get();
        assertNotNull(user);
    }

    @Test
    void shouldSaveUser(){
        User user = new User("test", "test", null, null, null, null, null, null, null, null, null, null, null);
        User savedUser = userService.save(user);
        assertNotNull(savedUser);
        userService.deleteById(savedUser.getId());
    }

    @Test
    void shouldDeleteUser(){
        User user = new User();
        userService.save(user);
        userService.deleteById(user.getId());
        assertThat(userService.findById(user.getId()).isEmpty());

    }

    @Test
    void shouldFindByName(){
        User user = new User("test", "test", null, null, null, null, null, null, null, null, null, null, null);
        userService.save(user);
        User foundUser = userService.getUserByUsername("test");
        assertNotNull(foundUser);
        userService.deleteById(user.getId());
    }

    @Test
    void shouldFindByNameAndTlf(){
        User user = new User("test", "test", null, "666666666", null, null, null, null, null, null, null, null, null);
        userService.save(user);
        User foundUser = userService.getUserByUsernameAndPhone("test", "666666666");
        assertNotNull(foundUser);
        userService.deleteById(user.getId());
    }

    @Test
    void shouldFindByOwner(){
        User user = new User("test", "test", true, "666666666", null, null, null, null, null, null, null, null, null);
        userService.save(user);
        List<User> foundUsers = userService.findByIsOwner(true);
        Boolean isOwner = true;
        for(User u: foundUsers){
            isOwner = u.getIsOwner();
        }
        assertTrue(isOwner);
        userService.deleteById(user.getId());

        User user2 = new User("test", "test", false, "666666666", null, null, null, null, null, null, null, null, null);
        userService.save(user2);
        foundUsers = userService.findByIsOwner(false);
        isOwner = false;
        for(User u: foundUsers){
            isOwner = u.getIsOwner();
        }
        assertFalse(isOwner);
        userService.deleteById(user2.getId());
    }

}
