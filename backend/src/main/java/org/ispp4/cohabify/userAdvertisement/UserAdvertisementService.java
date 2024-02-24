package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import org.springframework.security.access.AccessDeniedException;
import java.util.NoSuchElementException;

@Service
public class UserAdvertisementService {

    private UserAdvertisementRepository userAdvertisementRepository;

    @Autowired
    public UserAdvertisementService(UserAdvertisementRepository userAdvertisementRepository) {
        this.userAdvertisementRepository = userAdvertisementRepository;
    }
    
    /* 
    @Transactional
    public void deleteAdvertisementById(ObjectId advertisementId, ObjectId requestingUserId) {
        
        Optional<UserAdvertisement> optionalAdvertisement = userAdvertisementRepository.findById(advertisementId);

        if (optionalAdvertisement.isPresent()) {
            UserAdvertisement advertisement = optionalAdvertisement.get();

            if (advertisement.getUserId().equals(requestingUserId)) {
                userAdvertisementRepository.deleteById(advertisementId);
            } else {
                throw new AccessDeniedException("No puedes borrar un anuncio que no es tuyo");
            }
        } else {
            throw new NoSuchElementException("UserAdvertisement no encontrado");        
        }
    }
    */
}


