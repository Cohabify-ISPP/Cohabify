package org.ispp4.cohabify.user_advertisement;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.configuration.WebSecurityConfig;
import org.ispp4.cohabify.user.Gender;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import static org.mockito.ArgumentMatchers.any;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.mockito.InjectMocks;


@SpringBootTest
public class UserAdvertisementControllerTest {
    
    @Autowired
    private UserAdvertisementService userAdvertisementService;

    @Autowired
    private UserAdvertisementController userAdvertisementController;

    @Autowired
    private UserService userService;

    @Test
    public void testCreateExistingUserAdvertisement() {
        UserAdvertisement existingAd = userAdvertisementService.findAll().get(0);
        ResponseEntity<UserAdvertisement> response = userAdvertisementController.processCreationForm(existingAd);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    @Test
    public void testCreateNewUserAdvertisementWithCorrectData() {
        List<String> authorities = new ArrayList<String>();
        authorities.add("User");
        User user = new User("test", "test", null, null, "test@gmail.com", null, "descripcion test", Plan.BASIC, Gender.FEMENINO, null, true, authorities, null);
        user.setIsOwner(false);
        user.setPhone("666777666");
        userService.save(user);

        UserAdvertisement newAd = new UserAdvertisement();
        newAd.setAuthor(user);
        newAd.setDescription("Test Description");
        newAd.setMaxBudget(1000.0);
        newAd.setDesiredLocation("Test Location");
        newAd.setEntranceDate(LocalDate.now());
        newAd.setMaxCohabitants(2);
        
        ResponseEntity<UserAdvertisement> response = userAdvertisementController.processCreationForm(newAd);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newAd, response.getBody());

        userAdvertisementService.deleteUserAdvertisementById(newAd.getId());
        userService.deleteById(user.getId());
    }

    @Test
    public void testCreateNewUserAdvertisementWithAllFieldsNull() {
        UserAdvertisement nullAd = new UserAdvertisement();

        ResponseEntity<UserAdvertisement> response = userAdvertisementController.processCreationForm(nullAd);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}



       
