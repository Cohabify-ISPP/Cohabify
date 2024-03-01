package org.ispp4.cohabify.userRating;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/userRating")
@AllArgsConstructor
public class UserRatingController {
    
    @Autowired
    UserRatingService userRatingService;

    @GetMapping("/all")
    public ResponseEntity<List<UserRating>> getAllUserRatings() {
        try	{
            List<UserRating> userRatings = userRatingService.findAll();
            if (userRatings != null && !userRatings.isEmpty()) {
                return ResponseEntity.ok(userRatings);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/allByRatedUserId/{ratedUserId}")
    public ResponseEntity<List<UserRating>> getAllUserRatingsByRatedUserId(@PathVariable("ratedUserId") ObjectId ratedUserId) {
        try	{
            List<UserRating> userRatings = userRatingService.findByRatedUserId(ratedUserId);
            if (userRatings != null && !userRatings.isEmpty()) {
                return ResponseEntity.ok(userRatings);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/allByUserId/{userId}")
    public ResponseEntity<List<UserRating>> getAllUserRatingsByUserId(@PathVariable("userId") ObjectId userId) {
        try	{
            List<UserRating> userRatings = userRatingService.findByUserId(userId);
            if (userRatings != null && !userRatings.isEmpty()) {
                return ResponseEntity.ok(userRatings);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/findById/{userRatingId}")
    public ResponseEntity<UserRating> getUserRatingById(@PathVariable("userRatingId") ObjectId userRatingId) {
        try	{
            UserRating userRating = userRatingService.findById(userRatingId);
            if (userRating != null) {
                return ResponseEntity.ok(userRating);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/deleteByRatedUserId/{ratedUserId}")
    public ResponseEntity<Void> deleteUserRatingsByRatedUserId(@PathVariable("ratedUserId") ObjectId ratedUserId) {
        try	{
            userRatingService.deleteByRatedUserId(ratedUserId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/deleteById/{userRatingId}")
    public ResponseEntity<Void> deleteUserRatingById(@PathVariable("userRatingId") ObjectId userRatingId) {
        try	{
            userRatingService.deleteById(userRatingId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/deleteByUserId/{userId}")
    public ResponseEntity<Void> deleteUserRatingsByUserId(@PathVariable("userId") ObjectId userId) {
        try	{
            userRatingService.deleteByUserId(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("create")
    public ResponseEntity<UserRating> createUserRating(@RequestBody @Valid UserRating userRating) {
        try	{
            UserRating newUserRating = userRatingService.save(userRating);
            return ResponseEntity.ok(newUserRating);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("update/{userRatingId}")
    public ResponseEntity<UserRating> updateUserRating(@PathVariable("userRatingId") ObjectId userRatingId, @Valid @RequestBody UserRating userRating) {
        try	{
            UserRating _userRating= userRatingService.findById(userRatingId);
            if (_userRating != null) {
                _userRating.setId(userRatingId);
                _userRating.setComment(userRating.getComment());
                _userRating.setLike(userRating.getLike());
                _userRating.setRatedUserId(userRating.getRatedUserId());
                _userRating.setUserId(userRating.getUserId());
                UserRating updatedUserRating = userRatingService.save(_userRating);
                return ResponseEntity.ok(updatedUserRating);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    
    
}
