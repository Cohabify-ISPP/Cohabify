package org.ispp4.cohabify.houseAdvertisement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
public class HouseAdvertisementServiceTest {

    @Autowired
    private HouseAdvertisementService houseAdvertisementService;

    @Autowired
    private HouseAdvertisementRepository houseAdvertisementRepository;

    @Test
    public void testFindAll() {
        List<HouseAdvertisement> houseAdvertisements = houseAdvertisementRepository.findAll();
        PageRequest pageable = PageRequest.of(0, 10);

        List<HouseAdvertisement> result = houseAdvertisementService.findAll(pageable).getContent();

        assertNotEquals(result.size(), 0);
        assertNotEquals(houseAdvertisements.size(), 0);
    }

    @Test
    public void testFindByAuthorId() {
        ObjectId authorId = new ObjectId();
        List<HouseAdvertisement> houseAdvertisements = houseAdvertisementRepository.findByAuthorId(authorId);

        List<HouseAdvertisement> result = houseAdvertisementService.findByAuthorId(authorId);

        assertEquals(houseAdvertisements.size(), result.size());
    }

    @Test
    public void testFindById() {
        ObjectId id = new ObjectId();
        Optional<HouseAdvertisement> houseAdvertisement = houseAdvertisementRepository.findById(id);

        Optional<HouseAdvertisement> result = houseAdvertisementService.findById(id);

        assertEquals(houseAdvertisement, result);
    }

    @Test
    public void testFindAdById_NotFound() {
        ObjectId id = new ObjectId();

        HouseAdvertisement result = houseAdvertisementService.findAdById(id);

        assertNull(result);
    }

    @Test
    public void testSave() {
        HouseAdvertisement houseAdvertisement = new HouseAdvertisement();

        HouseAdvertisement savedAdvertisement = houseAdvertisementService.save(houseAdvertisement);

        assertNotNull(savedAdvertisement.getId());
    }

    @Test
    public void testUpdate() {
        HouseAdvertisement houseAdvertisement = new HouseAdvertisement();
        houseAdvertisement.setTitle("Old Title");
        HouseAdvertisement savedAdvertisement = houseAdvertisementRepository.save(houseAdvertisement);

        String newTitle = "New Title";
        houseAdvertisement.setTitle(newTitle);

        HouseAdvertisement updatedAdvertisement = houseAdvertisementService.update(savedAdvertisement.getId(), houseAdvertisement);

        assertEquals(newTitle, updatedAdvertisement.getTitle());
    }

    @Test
    public void testDeleteById() {
        HouseAdvertisement houseAdvertisement = new HouseAdvertisement();
        HouseAdvertisement savedAdvertisement = houseAdvertisementRepository.save(houseAdvertisement);

        houseAdvertisementService.deleteById(savedAdvertisement.getId());
        Optional<HouseAdvertisement> deletedAdvertisement = houseAdvertisementRepository.findById(savedAdvertisement.getId());

        assertFalse(deletedAdvertisement.isPresent());
    }
}
