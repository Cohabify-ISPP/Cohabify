package org.ispp4.cohabify.houseRating;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.ErrorResponse;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.HouseAdvertisementRatingRequest;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.utils.Global;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/houseRating")
@AllArgsConstructor
public class HouseRatingController {
    
    private HouseRatingService houseRatingService;
    private Global global;

    @GetMapping("")
    public ResponseEntity<List<HouseRating>> getAllHouseRatings() {
        try	{
            List<HouseRating> houseRatings = houseRatingService.findAll();
                return ResponseEntity.ok(houseRatings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/houseAdvertisements/{houseAdvertisementId}")
    public ResponseEntity<List<HouseRating>> getAllHouseRatingsByHouseAdvertisementId(@PathVariable("houseAdvertisementId") ObjectId houseAdvertisementId) {
        try	{
            List<HouseRating> houseRatings = houseRatingService.findByHouse(houseAdvertisementId);
            return ResponseEntity.ok(houseRatings);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{houseRatingId}")
    public ResponseEntity<HouseRating> gethouseRatingById(@PathVariable("houseRatingId") ObjectId houseRatingId) {
        try	{
            HouseRating houseRating = houseRatingService.findById(houseRatingId);
            if (houseRating != null) {
                return ResponseEntity.ok(houseRating);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/house/{houseId}")
    public ResponseEntity<Void> deleteHouseRatingsByHouseId(@PathVariable("houseId") ObjectId houseId) {
        try	{
            houseRatingService.deleteByUserId(houseId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{houseRatingId}")
    public ResponseEntity<Void> deleteHouseRatingById(@PathVariable("houseRatingId") ObjectId houseRatingId) {
        try	{
            houseRatingService.deleteById(houseRatingId);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createHouseRating(@RequestBody @Valid HouseAdvertisementRatingRequest request, BindingResult result) {
        try	{
            User currentUser = global.getCurrentUser();

            if(currentUser == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ErrorResponse("No puedes comentar sin estar logeado."));
            }

            if(currentUser.equals(request.getHouseAdvertisement().getAuthor())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse("No puedes ponerte una reseña a ti mismo."));
            }

            if(result.hasErrors()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                    .body(result.getFieldErrors()
                                                .stream()
                                                .map(fe -> new FormItemValidationError(fe))
                                                .toList());
            }
            
            HouseRating newHouseRating = new HouseRating();
            newHouseRating.setComment(request.getComment());
            newHouseRating.setDate(new Date(System.currentTimeMillis()));
            newHouseRating.setHouseAdvertisement(request.getHouseAdvertisement());
            newHouseRating.setRating(request.getRating());
            newHouseRating.setUser(currentUser);
            newHouseRating = houseRatingService.save(newHouseRating);
            return ResponseEntity.ok(newHouseRating);

        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}