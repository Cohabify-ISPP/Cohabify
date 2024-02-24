package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userAdvertisement")
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
    
	@GetMapping("/{userAdvertisementId}/delete")
	public ModelAndView deleteCause(@PathVariable("userAdvertisementId") ObjectId userAdvertisementId) {
		try {
			userAdvertisementService.deleteUserAdvertisementById(userAdvertisementId);
			ModelAndView res = new ModelAndView("redirect:/userAdvertisement");
			res.addObject("error", "El anuncio de usuario ha sido eliminado correctamente.");
			return res;
		
		} catch (Exception e) {
			ModelAndView res = new ModelAndView("redirect:/userAdvertisement");
			res.addObject("error", "Error al eliminar el anuncio de usuario.");
			return res;
		}
	}
}
