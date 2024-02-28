package org.ispp4.cohabify.houseAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseAdvertisementService {

    private final HouseAdvertisementRepository advertisementRepository;

    @Autowired
    public HouseAdvertisementService(HouseAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<HouseAdvertisement> findAll() {
        return advertisementRepository.findAll();
    }

    public Optional<HouseAdvertisement> findById(ObjectId id) {
        return advertisementRepository.findById(id);
    }

    public HouseAdvertisement save(HouseAdvertisement advertisement) {
        return (HouseAdvertisement) advertisementRepository.save(advertisement);
    }

    public void deleteById(ObjectId id) {
        advertisementRepository.deleteById(id);
    }
}
