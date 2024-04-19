package org.ispp4.cohabify.house;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.User;
import org.springframework.stereotype.Service;


import java.util.Optional;
import java.util.ArrayList;
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

    public List<House> getLikedHousesByUser(ObjectId userId) {
        List<House> allHouses = houseRepository.findAll();
        List<House> likedHouses = new ArrayList<>();
    
        for (House house : allHouses) {
            for (User like : house.getLikes()) {
                if (userId.equals(like.getId())) {
                    likedHouses.add(house);
                    break;
                }
            }
        }
    
        return likedHouses;
    }
    
    
}

