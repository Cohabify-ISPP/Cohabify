package org.ispp4.cohabify.house;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    @Autowired
    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> getAllHouses() {
        List<House> houses = houseRepository.findAll();
        return houses;
    }

    public House getHouseById(ObjectId id) {
        return houseRepository.findById(id).orElse(null);
    }

    public House saveHouse(House house) {
        House savedHouse = houseRepository.save(house);
        return savedHouse;
    }

    public void deleteHouse(ObjectId id) {
        houseRepository.deleteById(id);
    }

    public House getHouseByCadastre(int cadastre) {
        return houseRepository.findByCadastre(cadastre).orElse(null);
    }
    
}

