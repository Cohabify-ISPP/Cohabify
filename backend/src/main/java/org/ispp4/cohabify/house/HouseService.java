package org.ispp4.cohabify.house;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public ResponseEntity<List<House>> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return new ResponseEntity<>(houses, HttpStatus.OK);
    }

    public ResponseEntity<House> getHouseById(ObjectId id) {
        return houseRepository.findById(id)
                .map(house -> new ResponseEntity<>(house, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public ResponseEntity<House> saveHouse(House house) {
        House savedHouse = houseRepository.save(house);
        return new ResponseEntity<>(savedHouse, HttpStatus.CREATED);
    }

    public ResponseEntity<Void> deleteHouse(ObjectId id) {
        houseRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<House> getHouseByCadastre(int cadastre) {
        return houseRepository.findByCadastre(cadastre)
                .map(house -> new ResponseEntity<>(house, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
}

