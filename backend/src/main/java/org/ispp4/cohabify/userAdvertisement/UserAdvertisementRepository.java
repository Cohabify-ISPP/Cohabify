package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserAdvertisementRepository extends MongoRepository<UserAdvertisement, ObjectId>{

    public List<UserAdvertisement> findAll();
    public List<UserAdvertisement> findByAuthorId(ObjectId authorId);
    public void deleteById(ObjectId id);
    
}
