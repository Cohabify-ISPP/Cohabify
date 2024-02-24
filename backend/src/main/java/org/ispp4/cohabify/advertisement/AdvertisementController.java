package org.ispp4.cohabify.advertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AdvertisementController {
    
    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertisementController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Transactional(readOnly = true)
    @GetMapping("/advertisements")
    public ResponseEntity<List<Advertisement>> getAllAdvertisements() {
        List<Advertisement> advertisements = advertisementService.findAll();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @Transactional(readOnly = true)
    @GetMapping("/advertisements/{id}")
    public ResponseEntity<Advertisement> getAdvertisementById(ObjectId id) {
        Optional<Advertisement> advertisement = advertisementService.findById(id);
        return advertisement.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


}