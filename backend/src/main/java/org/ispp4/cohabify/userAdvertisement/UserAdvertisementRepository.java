package org.ispp4.cohabify.userAdvertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAdvertisementRepository extends MongoRepository<UserAdvertisement, ObjectId>{

    public List<UserAdvertisement> findAll();
    public Optional<UserAdvertisement> findByAuthorId(ObjectId authorId);
    public void deleteById(ObjectId id);
    
}
