package org.ispp4.cohabify.house;

import java.util.List;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.HouseRequest;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class HouseController {
    
    private final HouseService houseService;

    @Autowired
    public HouseController(HouseService houseService) {
        this.houseService = houseService;
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
        GeoJsonPoint point = new GeoJsonPoint(request.getX(), request.getY());
        house.setLocationPoint(point);
        house.setTags(request.getTags());
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
}
