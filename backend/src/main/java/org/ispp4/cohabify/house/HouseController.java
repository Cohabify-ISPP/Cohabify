package org.ispp4.cohabify.house;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.utils.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
    private Global global;

    @Autowired
    public HouseController(HouseService houseService, Global global) {
        this.houseService = houseService;
        this.global = global;
    }

    @Transactional(readOnly = true)
    @GetMapping("/houses")
    public ResponseEntity<List<House>> getAllHouses() {
        List<House> houses = houseService.findAll();
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    @PostMapping("/houses")
    public ResponseEntity<House> createHouse(@Valid @RequestBody House house) {
        try {
            Optional<House> houseExist = houseService.getHouseByCadastre(house.getCadastre());
            if(houseExist.isPresent()) {
				throw new Exception("User already has an house created");
			}
            if (house.getRoomsNumber() == 0 || house.getBathroomsNumber() == 0 || house.getArea() == 0
                    || house.getHeating() == null || house.getFloor() == 0 || house.getLocation() == null
                    || house.getLocationPoint() == null || house.getCadastre() == null || house.getTags() == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            House newHouse = houseService.save(house);
            return new ResponseEntity<>(newHouse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional(readOnly = true)
    @GetMapping("/houses/{id}")
    public ResponseEntity<House> getHouseById(ObjectId id) {
        House house = houseService.findById(id).get();
        if(house == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(house, HttpStatus.OK);
        }
    }

    @PutMapping("/houses/like/{id}")
    public ResponseEntity<House> toggleLike(@PathVariable("id") ObjectId id) {

        House house = houseService.findById(id).get();
        User raterUser = global.getCurrentUser(); 

        try {
            if (house == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                if (raterUser == null) {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                } else {
                    List<User> raters = house.getLikes();
                    Optional<User> foundUser = raters.stream().filter(x-> x.getId().equals(raterUser.getId())).findFirst();
                    
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