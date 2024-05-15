package org.ispp4.cohabify.house;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class HouseServiceTest {

    @Autowired
    private HouseService houseService;

    @Test
    void shouldFindAllHouses() {
        List<House> houses = this.houseService.findAll();
        assertNotNull(houses);
    }

    @Test
    void shouldFindHouseById() {
        ObjectId id = new ObjectId("60d5ec9af682fbd39a1b8b9f");
        House house = this.houseService.findById(id).get();
        assertNotNull(house);
    }

    @Test
    void shouldSaveHouse() {
        House house = new House();
        house = this.houseService.save(house);
        assertNotNull(house);
        this.houseService.deleteById(house.getId());
    }

    @Test
    void shouldDeleteHouse() {
        House house = new House();
        house = this.houseService.save(house);
        ObjectId id = house.getId();
        this.houseService.deleteById(id);
        Optional<House> optionalHouse = this.houseService.findById(id);
        assertTrue(optionalHouse.isEmpty());
    }


    @Test
    void shouldGetHouseByCadastre() {
        String cadastre = "12345678901234567890";
        House house = this.houseService.getHouseByCadastre(cadastre).get();
        assertNotNull(house);
    }
}
