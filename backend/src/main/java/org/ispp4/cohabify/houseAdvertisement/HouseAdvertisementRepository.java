package org.ispp4.cohabify.houseAdvertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseAdvertisementRepository extends MongoRepository<HouseAdvertisement, ObjectId>{
    
    Optional<HouseAdvertisement> findById(ObjectId id);

    List<HouseAdvertisement> findByAuthorId(ObjectId authorId);

    @Query("{ 'house._id' : ?0 }")
    Optional<HouseAdvertisement> findAdvertisementByHouseId(ObjectId houseId);

    List<HouseAdvertisement> findAll();

    void deleteById(ObjectId id);
}