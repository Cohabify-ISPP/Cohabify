package org.ispp4.cohabify.userAdvertisement;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.ispp4.cohabify.user.Plan;
import org.ispp4.cohabify.user.User;
import org.ispp4.cohabify.user.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/advertisements/users")
@AllArgsConstructor
public class UserAdvertisementController {

	private UserAdvertisementService userAdvertisementService;
	private UserService userService;
	
	@Override
	public String toString() {
		return "UserAdvertisementController [userAdvertisementService=" + userAdvertisementService + "]";
	}

	@Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<UserAdvertisement>> getAllUserAdvertisements(@Nullable Principal principal) {
        List<UserAdvertisement> userAdvertisements = userAdvertisementService.findAll();
		if(principal != null) {
			User user = userService.getUserByUsername(principal.getName());
			if(user.getPlan().equals(Plan.BASIC)) {
				userAdvertisements = userAdvertisements.stream()
														// Filter advertisements to leave the ones that are owned or that were created at least a day before now
														.filter(a -> a.getAuthor().equals(user) ||
																System.currentTimeMillis() > (a.getId().getTimestamp() & 0xFFFFFFFFL) * 1000L + 86400000)
														.toList();
			}
		}
        return new ResponseEntity<>(userAdvertisements, HttpStatus.OK);
    }

	@GetMapping("/{id}")
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
    
	@DeleteMapping("/{Id}")
	public ResponseEntity<HttpStatus> deleteUserAdvertisement(@PathVariable("Id") ObjectId userAdvertisementId) {
		try {
			userAdvertisementService.deleteUserAdvertisementById(userAdvertisementId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@PostMapping("")
	public ResponseEntity<UserAdvertisement> processCreationForm(@RequestBody UserAdvertisement userAdvertisement) {		
		try {
			UserAdvertisement res = userAdvertisementService.save(userAdvertisement);
			return new ResponseEntity<UserAdvertisement>(res, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}