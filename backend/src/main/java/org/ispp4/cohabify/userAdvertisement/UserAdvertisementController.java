package org.ispp4.cohabify.userAdvertisement;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.ispp4.cohabify.utils.Global;
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

import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/advertisements/users")
public class UserAdvertisementController {

	private UserAdvertisementService userAdvertisementService;
	private UserService userService;
	private Global global;
	
	public UserAdvertisementController(UserAdvertisementService userAdvertisementService, UserService userService , Global global) {
		this.userAdvertisementService = userAdvertisementService;
		this.userService = userService;
		this.global = global;
	}

	@Transactional(readOnly = true)
    @GetMapping("")
    public ResponseEntity<List<UserAdvertisement>> getAllUserAdvertisements(@Nullable Principal principal) {
        List<UserAdvertisement> userAdvertisements = userAdvertisementService.findAll();
		userAdvertisements = userAdvertisementService.checkPromotions(userAdvertisements);
		if(principal != null) {
			User user = userService.getUserByUsername(principal.getName());
			if(user.getPlan().equals(Plan.BASIC)) {
				userAdvertisements = userAdvertisements.stream()
														// Filter advertisements to leave the ones that are owned or that were created at least a day before now
														.filter(a -> a.getAuthor().getId().equals(user.getId()) || System.currentTimeMillis() > (a.getId().getTimestamp() & 0xFFFFFFFFL) * 1000L + 86400000)
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

	@GetMapping("/myAdvertisement/{authorId}")
    public ResponseEntity<UserAdvertisement> getUserAdvertisementByAuthorId(@PathVariable String authorId) {

		if (authorId == null || authorId.isEmpty() || userService.findById(new ObjectId(authorId)).isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Optional<UserAdvertisement> userAd = Optional.empty();

		try {
			ObjectId objId = new ObjectId(authorId);
			userAd = userAdvertisementService.findByAuthorId(objId);
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
			UserAdvertisement userAdvertisement = userAdvertisementService.findById(userAdvertisementId).get();
			if(userAdvertisement.getAuthor().getUsername().equals(global.getCurrentUser().getUsername())){
				userAdvertisementService.deleteUserAdvertisementById(userAdvertisementId);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

	@PostMapping("")
	public ResponseEntity<UserAdvertisement> createOrEditUserAd(@RequestBody UserAdvertisement userAdvertisement) {
		
		try {
			if(userAdvertisement.getAuthor().getUsername().equals(global.getCurrentUser().getUsername())){
				UserAdvertisement res = userAdvertisementService.save(userAdvertisement);
				return new ResponseEntity<UserAdvertisement>(res, HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PostMapping("promote/{id}")
	public ResponseEntity<Void> promoteAd(@PathVariable ObjectId id) {
		if(!userAdvertisementService.findById(id).get().getAuthor().getUsername().equals(global.getCurrentUser().getUsername())||!userAdvertisementService.findById(id).isPresent()){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }else{
            UserAdvertisement ad = userAdvertisementService.findById(id).get();
            ad.setPromotionExpirationDate(LocalDate.now().plusDays(1));
            userAdvertisementService.save(ad);
        return new ResponseEntity<>(HttpStatus.OK);
        }
	}
	
}