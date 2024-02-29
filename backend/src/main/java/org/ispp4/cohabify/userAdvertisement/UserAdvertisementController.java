package org.ispp4.cohabify.userAdvertisement;

import java.util.List;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
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

import org.ispp4.cohabify.userAdvertisement.UserAdvertisement;
import org.ispp4.cohabify.userAdvertisement.UserAdvertisementService;

@RestController
@RequestMapping("/api/userAdvertisement")
public class UserAdvertisementController {

	private UserAdvertisementService userAdvertisementService;
	
	@Override
	public String toString() {
		return "UserAdvertisementController [userAdvertisementService=" + userAdvertisementService + "]";
	}

	@Autowired
	public UserAdvertisementController(UserAdvertisementService userAdvertisementService) {
		this.userAdvertisementService = userAdvertisementService;
	}

	@Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<UserAdvertisement>> getAllUserAdvertisements() {
        List<UserAdvertisement> userAdvertisements = userAdvertisementService.findAll();
        return new ResponseEntity<>(userAdvertisements, HttpStatus.OK);
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
