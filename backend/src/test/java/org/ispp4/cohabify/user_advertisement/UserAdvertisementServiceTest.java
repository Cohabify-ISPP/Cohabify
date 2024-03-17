package org.ispp4.cohabify.user_advertisement;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.Gender;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        List<String> authorities = new ArrayList<String>();
        authorities.add("User");
        User user = new User("test", "test", null, null, "test@gmail.com", null, "descripcion test", Plan.BASIC, Gender.FEMENINO, null, true, authorities, null);
        user.setIsOwner(false);
        user.setPhone("666777666");
        us.save(user);

        Double budget = 600.0;
        Integer maxCohabitants = 1;
        UserAdvertisement userAd = new UserAdvertisement(new ObjectId("60d5ec9af682fbd39a1b8b8a"), "Descripcion", budget, "Sevilla", LocalDate.of(2025, 1, 15), LocalDate.of(2026, 1, 15),maxCohabitants,user);
        uas.save(userAd);
        UserAdvertisement fetchedUAD = uas.findById(new ObjectId("60d5ec9af682fbd39a1b8b8a")).get();
        assertThat(fetchedUAD.getId()).isEqualTo(userAd.getId());

        us.deleteById( user.getId());
        uas.deleteUserAdvertisementById(userAd.getId());

    }

    @Test
    void shouldNotCreateUA(){
        UserAdvertisement userAd = new UserAdvertisement();
        assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
        userAd.setDesiredLocation("");
        assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
                
    }
    
}
