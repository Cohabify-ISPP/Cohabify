package org.ispp4.cohabify.advertisement;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    @Autowired
    public AdvertisementService(AdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<Advertisement> findAll() {
        return advertisementRepository.findAll();
    }

    public Optional<Advertisement> findById(ObjectId id) {
        return advertisementRepository.findById(id);
    }

    public Advertisement save(Advertisement advertisement) {
        return (Advertisement) advertisementRepository.save(advertisement);
    }

    public void deleteById(ObjectId id) {
        advertisementRepository.deleteById(id);
    }
}
