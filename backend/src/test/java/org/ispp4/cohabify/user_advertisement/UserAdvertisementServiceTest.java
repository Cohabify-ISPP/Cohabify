package org.ispp4.cohabify.user_advertisement;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.UserService;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;


import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJpaTest(includeFilters = @ComponentScan.Filter(Service.class))
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
        Double budget = 600.0;
        Integer maxCohabitants = 1;
        UserAdvertisement userAd = new UserAdvertisement(new ObjectId("60d5ec9af682fbd39a1b8b7z"), "Descripcion", budget, "Sevilla", LocalDate.of(2025, 1, 15), LocalDate.of(2026, 1, 15),maxCohabitants,us.findAll().get(1));
        uas.save(userAd);
        UserAdvertisement fetchedUAD = uas.findById(new ObjectId("60d5ec9af682fbd39a1b8b7z")).get();
        assertThat(fetchedUAD).isEqualTo(userAd);

    }

    @Test
    void shouldNotCreateUA(){
        UserAdvertisement userAd = new UserAdvertisement();
        assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
        userAd.setDesiredLocation("");
        assertThrows(IllegalArgumentException.class, () -> uas.save(userAd));
                
    }
    
}
