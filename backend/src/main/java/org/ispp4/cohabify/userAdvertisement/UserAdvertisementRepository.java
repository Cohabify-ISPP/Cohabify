package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserAdvertisementRepository extends MongoRepository<UserAdvertisement, ObjectId>{

    public List<UserAdvertisement> findAll();
    public Optional<UserAdvertisement> findById(ObjectId id);
    public List<UserAdvertisement> findByUserId(ObjectId userId);
    public void deleteById(ObjectId id);
    
}
