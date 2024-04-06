package org.ispp4.cohabify.userRating;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.ErrorResponse;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.UserRatingRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/userRating")
@AllArgsConstructor
public class UserRatingController {
    
    @Autowired
    UserRatingService userRatingService;

    @GetMapping("")
    public ResponseEntity<List<UserRating>> getAllUserRatings() {
        try	{
            List<UserRating> userRatings = userRatingService.findAll();
                return ResponseEntity.ok(userRatings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    } 

    @GetMapping("/ratedUser/{ratedUserId}")
    public ResponseEntity<List<UserRating>> getAllUserRatingsByRatedUserId(@PathVariable("ratedUserId") ObjectId ratedUserId) {
        try	{
            List<UserRating> userRatings = userRatingService.findByRatedUserId(ratedUserId);
            return ResponseEntity.ok(userRatings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserRating>> getAllUserRatingsByUserId(@PathVariable("userId") ObjectId userId) {
        try	{
            List<UserRating> userRatings = userRatingService.findByUserId(userId);
            return ResponseEntity.ok(userRatings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{userRatingId}")
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

    @DeleteMapping("/ratedUser/{ratedUserId}")
    public ResponseEntity<Void> deleteUserRatingsByRatedUserId(@PathVariable("ratedUserId") ObjectId ratedUserId) {
        try	{
            userRatingService.deleteByRatedUserId(ratedUserId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    @DeleteMapping("/{userId}/{ratedUserId}")
    public ResponseEntity<Void> deleteUserRatingsByRatedUserId(@PathVariable("ratedUserId") ObjectId ratedUserId, @PathVariable("userId") ObjectId userId) {
        try	{
            List<UserRating> userRatings = userRatingService.findByUserIdAndRatedUserId(ratedUserId, userId);
            for(UserRating u: userRatings){
                userRatingService.deleteById(u.getId());
            }
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/user/{userId}")
    public ResponseEntity<Void> deleteUserRatingsByUserId(@PathVariable("userId") ObjectId userId) {
        try	{
            userRatingService.deleteByUserId(userId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{userRatingId}")
    public ResponseEntity<Void> deleteUserRatingById(@PathVariable("userRatingId") ObjectId userRatingId) {
        try	{
            userRatingService.deleteById(userRatingId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
 
    @PostMapping("")
    public ResponseEntity<?> register(@Valid @RequestPart("string-data") UserRatingRequest request, BindingResult result) throws BadRequestException {
        if(result.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(result.getFieldErrors()
                                             .stream()
                                             .map(fe -> new FormItemValidationError(fe))
                                             .toList());
        }
        // Verificar si el user y el ratedUser son iguales
        if(request.getUser().getId().equals(request.getRatedUser().getId())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    
        UserRating userRating  = new UserRating();
        userRating.setComment(request.getComment());
        userRating.setUser(request.getUser());
        try{
            userRating.setRatedUser(request.getRatedUser());
            userRating = userRatingService.save(userRating); 
            return ResponseEntity.status(HttpStatus.CREATED)
                                .body(userRating);
        } catch (IllegalStateException e) {
            ErrorResponse error = new ErrorResponse(e.getMessage());            
            return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(error);
        }catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
    
    @PutMapping("")
    public ResponseEntity<UserRating> updateUserRating(@Valid @RequestBody UserRating userRating) {
        try	{
            UserRating updatedUserRating = userRatingService.save(userRating);
            return ResponseEntity.ok(updatedUserRating);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        } 
    }   
}