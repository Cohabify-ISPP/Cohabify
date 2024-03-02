package org.ispp4.cohabify.houseAdvertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface HouseAdvertisementRepository extends MongoRepository<HouseAdvertisement, ObjectId>{
    
    Optional<HouseAdvertisement> findById(ObjectId id);

    List<HouseAdvertisement> findAll();

    void deleteById(ObjectId id);
}