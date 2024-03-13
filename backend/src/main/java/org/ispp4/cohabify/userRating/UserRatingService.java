package org.ispp4.cohabify.userRating;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.commentAdvertisement.CommentAdvertisement;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserRatingService {

    UserRatingRepository userRatingRepository;

    public UserRating save(UserRating userRating) {
        return (UserRating) userRatingRepository.save(userRating);
    }

    @Transactional
    public void deleteByUserId(ObjectId userId) {
        userRatingRepository.deleteByUserId(userId);
    }

    @Transactional
    public void deleteByRatedUserId(ObjectId ratedUserId) {
        userRatingRepository.deleteByRatedUserId(ratedUserId);
    }
    

    @Transactional
    public void deleteById(ObjectId id) {
        userRatingRepository.deleteById(id);
    }

    @Transactional
    public UserRating findById(ObjectId id) {
        return userRatingRepository.findById(id).orElse(null);
    }

    @Transactional
    public List<UserRating> findByUserId(ObjectId userId) {
        return userRatingRepository.findByUserId(userId);
    }
    
    @Transactional
    public List<UserRating> findByUserIdAndRatedUserId(ObjectId ratedUserId, ObjectId userId) {
        return userRatingRepository.findByUserIdAndRatedUserId(userId, ratedUserId);
    }

    public List<UserRating> findByRatedUserId(ObjectId ratedUserId) {
        return userRatingRepository.findByRatedUserId(ratedUserId);
    }

    @Transactional
    public List<UserRating> findAll() {
        return userRatingRepository.findAll();
    }

    
    
}
