package org.ispp4.cohabify.userAdvertisement;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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

	@Transactional(readOnly = true)
    @GetMapping("/userAdvertisements")
    public ResponseEntity<List<UserAdvertisement>> getAllUserAdvertisements() {
        List<UserAdvertisement> userAdvertisements = userAdvertisementService.findAll();
        return new ResponseEntity<>(userAdvertisements, HttpStatus.OK);
    }

	@GetMapping("/userAdvertisements/{id}")
    public ResponseEntity<UserAdvertisement> getUserAdvertisement(@PathVariable String id) {
        Optional<UserAdvertisement> userAd = userAdvertisementService.findById(new ObjectId(id));
        if(userAd.isPresent()){
            return new ResponseEntity<>(userAd.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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
