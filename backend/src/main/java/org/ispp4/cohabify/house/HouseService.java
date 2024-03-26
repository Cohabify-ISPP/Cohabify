package org.ispp4.cohabify.house;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.List;

@Service
public class HouseService {

    private final HouseRepository houseRepository;

    public HouseService(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    public List<House> findAll() {
        List<House> houses = houseRepository.findAll();
        return houses;
    }

    public Optional<House> findById(ObjectId id) {
        return houseRepository.findById(id);
    } 

    
    public House save(House house) {
        House savedHouse = houseRepository.save(house);
        return savedHouse;
    }
    
    

    public void deleteById(ObjectId id) {
        houseRepository.deleteById(id);
    }

    public Optional<House> getHouseByCadastre(String cadastre) {
        return houseRepository.findByCadastre(cadastre);
    }
    
}

