package org.ispp4.cohabify.userAdvertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/userAdvertisement")
public class UserAdvertisementController {

	private UserAdvertisementService userAdvertisementService;
	
	@Override
	public String toString() {
		return "UserAdvertisementController [userAdvertisementService=" + userAdvertisementService + "]";
	}

	public UserAdvertisementController(UserAdvertisementService userAdvertisementService) {
		this.userAdvertisementService = userAdvertisementService;
	}

	@Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<UserAdvertisement>> getAllUserAdvertisements() {
        List<UserAdvertisement> userAdvertisements = userAdvertisementService.findAll();
        return new ResponseEntity<>(userAdvertisements, HttpStatus.OK);
    }

	@GetMapping("{id}")
    public ResponseEntity<UserAdvertisement> getUserAdvertisement(@PathVariable String id) {

		Optional<UserAdvertisement> userAd = Optional.empty();

		try {
			ObjectId objId = new ObjectId(id);
			userAd = userAdvertisementService.findById(objId);
		} catch (IllegalArgumentException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
        if(userAd.isPresent()){
            return new ResponseEntity<>(userAd.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
	@DeleteMapping("{userAdvertisementId}")
	public ResponseEntity<HttpStatus> deleteUserAdvertisement(@PathVariable("userAdvertisementId") ObjectId userAdvertisementId) {
		try {
			userAdvertisementService.deleteUserAdvertisementById(userAdvertisementId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@PostMapping()
	public ResponseEntity<UserAdvertisement> processCreationForm(@RequestBody UserAdvertisement userAdvertisement) {		
		try {
			UserAdvertisement res = userAdvertisementService.save(userAdvertisement);
			return new ResponseEntity<UserAdvertisement>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}