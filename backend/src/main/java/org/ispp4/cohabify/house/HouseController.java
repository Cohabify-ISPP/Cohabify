package org.ispp4.cohabify.house;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.HouseRequest;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class HouseController {
    
    private final HouseService houseService;
    private final UserService userService;

    public HouseController(HouseService houseService, UserService userService) {
        this.houseService = houseService;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    @GetMapping("/houses")
    public ResponseEntity<List<House>> getAllHouses() {
        List<House> houses = houseService.findAll();
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/houses")
	public ResponseEntity<?> register(@Valid @RequestBody HouseRequest request, BindingResult result) throws BadRequestException {
		
		if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(result.getFieldErrors()
										 	 .stream()
										 	 	.map(fe -> new FormItemValidationError(fe))
										 	 	.toList());
		}
		
		House house = new House();
		house.setRoomsNumber(request.getRoomsNumber());
		house.setBathroomsNumber(request.getBathroomsNumber());
        house.setArea(request.getArea());
		house.setFloor(request.getFloor());
        house.setLocation(request.getLocation());
		house.setCadastre(request.getCadastre());
        house.setHeating(request.getHeating());
        GeoJsonPoint point = new GeoJsonPoint(2, 2);
        house.setLocationPoint(point);
		house = houseService.save(house);
		// TODO: Add the user full name when it is fixed in the model
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(house);
	}

    @Transactional(readOnly = true)
    @GetMapping("/houses/{id}")
    public ResponseEntity<House> getHouseById(ObjectId id) {
        House house = houseService.findById(id);
        if(house == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(house, HttpStatus.OK);
        }
    }

    @PutMapping("/houses/like/{id}/{raterId}")
    public ResponseEntity<House> modifyRaters(@PathVariable("id") ObjectId id,
            @PathVariable("raterId") ObjectId raterId) {

            House house = houseService.findById(id);
        try {
            if (house == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
            } else {    
                Optional<User> optionalRaterUser = userService.findById(raterId);
                if (optionalRaterUser.isPresent() == false) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);

                } else {
                    User raterUser = optionalRaterUser.get();
                    List<User> raters = house.getLikes();
                    Optional<User> foundUser = raters.stream().filter(x-> x.getId().equals(raterId)).findFirst();
                    
                    if (foundUser.isPresent()) {
                        raters.remove(foundUser.get());
                    } else {
                        raters.add(raterUser);
                    }
                    house.setLikes(raters);
                    house = houseService.save(house);
                    return new ResponseEntity<House>(house, HttpStatus.OK);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}