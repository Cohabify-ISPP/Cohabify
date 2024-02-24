package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.util.NoSuchElementException;

import org.ispp4.cohabify.utils.GlobalVariables;
import org.ispp4.cohabify.user.User;

@Service
public class UserAdvertisementService {

    private final UserAdvertisementRepository userAdvertisementRepository;
    private final GlobalVariables globalVariables;

    @Autowired
    public UserAdvertisementService(UserAdvertisementRepository userAdvertisementRepository, GlobalVariables globalVariables) {
        this.userAdvertisementRepository = userAdvertisementRepository;
        this.globalVariables = globalVariables;
    }
    
    @Transactional
    public void deleteUserAdvertisementById(ObjectId advertisementId) {
        
        Optional<UserAdvertisement> userAdvertisement = userAdvertisementRepository.findById(advertisementId);
        User loggedInUser = globalVariables.getLoggedInUser();

        if (userAdvertisement.isPresent()) {

            UserAdvertisement advertisement = userAdvertisement.get();
            ObjectId publisher = advertisement.getUser().getId();

            if (publisher.equals(loggedInUser.getId()) || loggedInUser.isAdmin()) {
                userAdvertisementRepository.deleteById(advertisementId);
            } else {
                throw new AccessDeniedException("No puedes borrar un anuncio que no es tuyo");
            }
        } else {
            throw new NoSuchElementException("Anuncio de usuario no encontrado");        
        }
    }

}


