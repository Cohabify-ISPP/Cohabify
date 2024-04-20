package org.ispp4.cohabify.userRating;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;



@SpringBootTest
public class UserRatingServiceTest {
    
    @Autowired
    private UserRatingService userRatingService;

    @Autowired
    private UserRatingRepository userRatingRepository;

    @Autowired
    private UserRepository userRepository;
    
    @BeforeEach
    public void setup(){
        userRatingRepository.deleteAll();
    }

    @Test
    public void testSaveUserRating() {
        UserRating userRating = new UserRating();
        userRating.setComment("Texto");
        UserRating savedUserRating = userRatingService.save(userRating);

        assertNotNull(savedUserRating);
        assertEquals(userRating, savedUserRating);
    }

    @Test
    public void testDeleteByUserId() {
        ObjectId userId = new ObjectId();
        userRatingService.deleteByUserId(userId);

        List<UserRating> userRatings = userRatingService.findByUserId(userId);
        assertEquals(0, userRatings.size());
    }

    @Test
    public void testDeleteByRatedUserId() {
        ObjectId ratedUserId = new ObjectId();
        userRatingService.deleteByRatedUserId(ratedUserId);

        List<UserRating> userRatings = userRatingService.findByRatedUserId(ratedUserId);
        assertEquals(0, userRatings.size());
    }
    @Test
    public void testDeleteById() {
        ObjectId id = new ObjectId();
        UserRating userRating = new UserRating();
        userRating.setId(id);
        userRatingRepository.save(userRating);

        userRatingService.deleteById(id);

        UserRating deletedUserRating = userRatingService.findById(id);
        assertNull(deletedUserRating);
    }

    @Test
    public void testFindById() {
        ObjectId id = new ObjectId();
        UserRating userRating = new UserRating();
        userRating.setId(id);
        userRatingRepository.save(userRating);

        UserRating foundUserRating = userRatingService.findById(id);

        assertNotNull(foundUserRating);
        assertEquals(id, foundUserRating.getId());
    }

    @Test
    public void testFindByUserId() {
        User user = new User();
        user = userRepository.save(user);
        UserRating userRating = new UserRating();
        userRating.setUser(user);
        userRatingRepository.save(userRating);
        List<UserRating> userRatings = userRatingService.findByUserId(user.getId());

        assertEquals(1, userRatings.size());
        assertEquals(user.getId(), userRatings.get(0).getUser().getId());
    }

    @Test
    public void testFindByUserIdAndRatedUserId() {
        User user = new User();
        User ratedUser = new User();
        user = userRepository.save(user);
        ratedUser = userRepository.save(ratedUser);
        UserRating userRating = new UserRating();
        userRating.setUser(user);
        userRating.setRatedUser(ratedUser);
        userRatingRepository.save(userRating);

        List<UserRating> userRatings = userRatingService.findByUserIdAndRatedUserId(ratedUser.getId(), user.getId());

        assertEquals(1, userRatings.size());
        assertEquals(user.getId(), userRatings.get(0).getUser().getId());
        assertEquals(ratedUser.getId(), userRatings.get(0).getRatedUser().getId());
    } 

    @Test
    public void testFindByRatedUserId() {
        User ratedUser = new User();
        ratedUser = userRepository.save(ratedUser);
        UserRating userRating = new UserRating();
        userRating.setRatedUser(ratedUser);
        userRatingRepository.save(userRating);

        List<UserRating> userRatings = userRatingService.findByRatedUserId(ratedUser.getId());

        assertEquals(1, userRatings.size());
        assertEquals(ratedUser.getId(), userRatings.get(0).getRatedUser().getId());
    } 

    @Test
    public void testFindAll() {
        UserRating userRating1 = new UserRating();
        UserRating userRating2 = new UserRating();
        userRatingRepository.save(userRating1);
        userRatingRepository.save(userRating2);

        List<UserRating> userRatings = userRatingService.findAll();

        assertEquals(2, userRatings.size());
    }
}
