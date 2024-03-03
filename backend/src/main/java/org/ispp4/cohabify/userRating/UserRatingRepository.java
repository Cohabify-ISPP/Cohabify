package org.ispp4.cohabify.userRating;


import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRatingRepository extends MongoRepository<UserRating, ObjectId>{

    public List<UserRating> findByUserIdAndRatedUserId(ObjectId userId, ObjectId ratedUserId);
    public List<UserRating> findByUserId(ObjectId userId);
    public List<UserRating> findByRatedUserId(ObjectId ratedUserId);
    public void deleteByUserId(ObjectId userId);
    public List<UserRating> findAll();
    public Optional<UserRating> findById(ObjectId id);
    public void deleteByRatedUserId(ObjectId ratedUserId);
    
}
