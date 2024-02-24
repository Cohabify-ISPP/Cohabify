package org.ispp4.cohabify.userAdvertisement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class UserAdvertisementController {

	private UserAdvertisementService userAdvertisementService;
	
	@Autowired
	public UserAdvertisementController(UserAdvertisementService userAdvertisementService) {
		this.userAdvertisementService = userAdvertisementService;
	}
    
}
