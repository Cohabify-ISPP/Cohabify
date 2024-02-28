package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.util.List;
import java.util.NoSuchElementException;

import org.ispp4.cohabify.utils.Global;
import org.ispp4.cohabify.user.User;

@Service
public class UserAdvertisementService {

    private final UserAdvertisementRepository userAdvertisementRepository;
    private final Global globalVariables;

    @Autowired
    public UserAdvertisementService(UserAdvertisementRepository userAdvertisementRepository, Global globalVariables) {
        this.userAdvertisementRepository = userAdvertisementRepository;
        this.globalVariables = globalVariables;
    }

    public List<UserAdvertisement> findAll() {
        return userAdvertisementRepository.findAll();
    }
    
    @Transactional
    public void deleteUserAdvertisementById(ObjectId advertisementId) {
        
        Optional<UserAdvertisement> userAdvertisement = userAdvertisementRepository.findById(advertisementId);
        User loggedInUser = globalVariables.getCurrentUser();

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