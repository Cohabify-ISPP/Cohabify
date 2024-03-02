package org.ispp4.cohabify.houseAdvertisement;

import java.util.List;
import java.util.Optional;

import org.apache.coyote.BadRequestException;
import org.bson.types.ObjectId;
import org.ispp4.cohabify.dto.AdvertisementHouseRequest;
import org.ispp4.cohabify.dto.FormItemValidationError;
import org.ispp4.cohabify.dto.UserRegisterRequest;
import org.ispp4.cohabify.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/advertisements/houses")
public class HouseAdvertisementController {
    
    private final HouseAdvertisementService advertisementService;

    @Autowired
    public HouseAdvertisementController(HouseAdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<HouseAdvertisement>> getAllAdvertisements() {
        List<HouseAdvertisement> advertisements = advertisementService.findAll();
        return new ResponseEntity<>(advertisements, HttpStatus.OK);
    }

    @GetMapping("/advertisements/{id}")
    public ResponseEntity<HouseAdvertisement> getAdvertisement(@PathVariable ObjectId id) {
        Optional<HouseAdvertisement> advertisement = advertisementService.findById(id);
        if(advertisement.isPresent()){
            return new ResponseEntity<>(advertisement.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<HouseAdvertisement> getAdvertisement(@PathVariable String id) {
        Optional<HouseAdvertisement> advertisement = advertisementService.findById(new ObjectId(id));
        if(advertisement.isPresent()){
            return new ResponseEntity<>(advertisement.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/advertisements")
	public ResponseEntity<?> register(@Valid @RequestBody AdvertisementHouseRequest request, BindingResult result) throws BadRequestException {
		
		if(result.hasErrors()) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST)
								 .body(result.getFieldErrors()
										 	 .stream()
										 	 	.map(fe -> new FormItemValidationError(fe))
										 	 	.toList());
		}
		
		HouseAdvertisement advertisement = new HouseAdvertisement();
		advertisement.setTitle(request.getTitle());
		advertisement.setDescription(request.getDescription());
        advertisement.setPrice(request.getPrice());
        advertisement.setTenants(request.getTenants());
		advertisement = advertisementService.save(advertisement);
		// TODO: Add the user full name when it is fixed in the model
		
		return ResponseEntity.status(HttpStatus.CREATED)
							 .body(advertisement);
	}

    @PutMapping("/advertisements/{id}")
    public ResponseEntity<HouseAdvertisement> updateAdvertisement(@PathVariable ObjectId id, @RequestBody HouseAdvertisement advertisement) {
        HouseAdvertisement updatedAdvertisement = advertisementService.update(id, advertisement);
        return new ResponseEntity<>(updatedAdvertisement, HttpStatus.OK);
    }

    @DeleteMapping("/advertisements/{id}")
    public ResponseEntity<Void> deleteAdvertisement(@PathVariable ObjectId id) {
        advertisementService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

