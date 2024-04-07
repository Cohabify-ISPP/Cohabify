package org.ispp4.cohabify.houseAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class HouseAdvertisementService {

    private final HouseAdvertisementRepository advertisementRepository;

    public HouseAdvertisementService(HouseAdvertisementRepository advertisementRepository) {
        this.advertisementRepository = advertisementRepository;
    }

    public List<HouseAdvertisement> findAll() {
        return advertisementRepository.findAll();
    }

    public List<HouseAdvertisement> findByAuthorId(ObjectId authorId) {
        return advertisementRepository.findByAuthorId(authorId);
    }

    public Optional<HouseAdvertisement> findById(ObjectId id) {
        return advertisementRepository.findById(id);
    }

    public HouseAdvertisement findAdById(ObjectId id) {
        return advertisementRepository.findById(id).orElse(null);
    }

    public HouseAdvertisement save(HouseAdvertisement advertisement) {
        return (HouseAdvertisement) advertisementRepository.save(advertisement);
    }
    
    public HouseAdvertisement update(ObjectId id, HouseAdvertisement advertisement) {
        Optional<HouseAdvertisement> existingAdvertisement = advertisementRepository.findById(id);
        if (existingAdvertisement.isPresent()) {
            HouseAdvertisement adv = existingAdvertisement.get();

            adv.setTitle(advertisement.getTitle());
            adv.setDescription(advertisement.getDescription());
            adv.setPrice(advertisement.getPrice());
            /*
            adv.setImages(advertisement.getImages());
            */
            adv.setTenants(advertisement.getTenants());
            advertisement = advertisementRepository.save(advertisement);

            return advertisementRepository.save(adv);
        } else {
            return advertisementRepository.save(advertisement);
        }
}


    public void deleteById(ObjectId id) {
        advertisementRepository.deleteById(id);
    }

    public List<HouseAdvertisement> checkPromotions(List<HouseAdvertisement> advertisements) {
        for (HouseAdvertisement advertisement : advertisements) {
            if (advertisement.getPromotionExpirationDate() != null && advertisement.getPromotionExpirationDate().isBefore(LocalDate.now())) {
                advertisement.setPromotionExpirationDate(null);
                advertisementRepository.save(advertisement);
            }
        }
        return advertisements;

    }
}
