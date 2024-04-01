package org.ispp4.cohabify.user_advertisement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementController;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@SpringBootTest
public class UserAdvertisementControllerTest {
    
    @Autowired
    private UserAdvertisementService userAdvertisementService;

    @Autowired
    private UserAdvertisementController userAdvertisementController;

    @Test
    public void testCreateExistingUserAdvertisement() {
        UserAdvertisement existingAd = userAdvertisementService.findAll().get(0);
        ResponseEntity<UserAdvertisement> response = userAdvertisementController.createOrEditUserAd(existingAd);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }

    
    // @Test
    // public void testCreateNewUserAdvertisementWithCorrectData() {
    //     List<String> authorities = new ArrayList<>();
    //     authorities.add("User");
    //     User user = new User();
    //     user.setUsername("testAd");
    //     user.setPassword("Test_1512*123oa");
    //     user.setIsOwner(true);
    //     user.setPhone("666666666");
    //     user.setEmail("test@gmail.com");
    //     user.setDescription("test description");
    //     user.setPlan(Plan.BASIC);
    //     user.setGender(Gender.FEMENINO);
    //     user.setIsVerified(false);
    //     user.setAuthorities(authorities);
    //     user = userService.save(user);

    //     UserAdvertisement newAd = new UserAdvertisement();
    //     newAd.setAuthor(user);
    //     newAd.setDescription("Test Description");
    //     newAd.setMaxBudget(1000.0);
    //     newAd.setDesiredLocation("Test Location");
    //     newAd.setEntranceDate(LocalDate.now());
    //     newAd.setMaxCohabitants(2);
        
    //     ResponseEntity<UserAdvertisement> response = userAdvertisementController.createOrEditUserAd(newAd);
    //     userService.deleteById(user.getId());

    //     assertEquals(HttpStatus.CREATED, response.getStatusCode());
    //     assertEquals(newAd, response.getBody());
    //     userAdvertisementService.deleteUserAdvertisementById(response.getBody().getId());

    // }

    @Test
    public void testCreateNewUserAdvertisementWithAllFieldsNull() {
        UserAdvertisement nullAd = new UserAdvertisement();

        ResponseEntity<UserAdvertisement> response = userAdvertisementController.createOrEditUserAd(nullAd);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
    }
}



       
