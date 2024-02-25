package org.ispp4.cohabify.advertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AdvertisementRepository extends MongoRepository<Advertisement, ObjectId>{
    
    Optional<Advertisement> findById(ObjectId id);

    List<Advertisement> findAll();

    void deleteById(ObjectId id);
}