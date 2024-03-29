package org.ispp4.cohabify.user_advertisement;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.Gender;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserAdvertisementServiceTest {

    @Autowired
    UserAdvertisementService uas;

    @Autowired
    UserService us;

    @Test
    void shouldGetAllUA(){
        List<UserAdvertisement> userAds = uas.findAll();
        assertThat(userAds.size()).isGreaterThan(1);

    }

    @Test
    void shouldCreateUA(){
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
        user = us.save(user);

        Double budget = 600.0;
        Integer maxCohabitants = 1;
        UserAdvertisement userAd = new UserAdvertisement(new ObjectId("60d5ec9af682fbd39a1b8b8a"), "Descripcion", budget, "Sevilla", LocalDate.of(2025, 1, 15), LocalDate.of(2026, 1, 15),maxCohabitants,user);
        uas.save(userAd);
        UserAdvertisement fetchedUAD = uas.findById(new ObjectId("60d5ec9af682fbd39a1b8b8a")).get();
        assertThat(fetchedUAD.getId()).isEqualTo(userAd.getId());

        us.deleteById( user.getId());
        uas.deleteUserAdvertisementById(userAd.getId());

    }

//    @Test
 //   void shouldNotCreateUA(){
  //      UserAdvertisement userAd = new UserAdvertisement();
   //     assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
     //   userAd.setDesiredLocation("");
     //   assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
                
   // }
    
}
