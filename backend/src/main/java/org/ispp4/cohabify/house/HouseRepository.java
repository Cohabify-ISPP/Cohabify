package org.ispp4.cohabify.house;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends MongoRepository<House, ObjectId> {
    
    @Query("{ 'cadastre' : ?0 }")
    Optional<House> findByCadastre(int cadastre);
    
}
