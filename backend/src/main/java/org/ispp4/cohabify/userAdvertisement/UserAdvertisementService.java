package org.ispp4.cohabify.userAdvertisement;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.AccessDeniedException;

import java.util.Optional;
import java.time.LocalDate;
import java.util.List;

import org.ispp4.cohabify.houseAdvertisement.HouseAdvertisement;
import org.ispp4.cohabify.utils.Global;
//import org.ispp4.cohabify.user.User;

@Service
public class UserAdvertisementService {

    private final UserAdvertisementRepository userAdvertisementRepository;
    //private final Global globalVariables;

    public UserAdvertisementService(UserAdvertisementRepository userAdvertisementRepository, Global globalVariables) {
        this.userAdvertisementRepository = userAdvertisementRepository;
        //this.globalVariables = globalVariables;
    }

    public List<UserAdvertisement> findAll() {
        return userAdvertisementRepository.findAll();
    }

    public Optional<UserAdvertisement> findById(ObjectId id) {
        return userAdvertisementRepository.findById(id);
    }

    public Optional<UserAdvertisement> findByAuthorId(ObjectId authorId) {
        return userAdvertisementRepository.findByAuthorId(authorId);
    }
    
    @Transactional
    public void deleteUserAdvertisementById(ObjectId advertisementId) {
        
        Optional<UserAdvertisement> userAdvertisement = userAdvertisementRepository.findById(advertisementId);
        //User loggedInUser = globalVariables.getCurrentUser();

        if (userAdvertisement.isPresent()) {

            //ObjectId publisher = advertisement.getUser().getId();
            //if (publisher.equals(loggedInUser.getId()) || loggedInUser.isAdmin()) {
                userAdvertisementRepository.deleteById(advertisementId);
            } else {
                throw new AccessDeniedException("No puedes borrar un anuncio que no es tuyo");
            }
        //} else {
        //    throw new NoSuchElementException("Anuncio de usuario no encontrado");        
        //}
    }

    public UserAdvertisement save(UserAdvertisement userAdvertisement) {
        return userAdvertisementRepository.save(userAdvertisement);
    }

    public List<UserAdvertisement> checkPromotions(List<UserAdvertisement> advertisements) {
        for (UserAdvertisement advertisement : advertisements) {
            if (advertisement.getPromotionExpirationDate() != null && advertisement.getPromotionExpirationDate().isBefore(LocalDate.now())) {
                advertisement.setPromotionExpirationDate(null);
                userAdvertisementRepository.save(advertisement);
            }
        }
        return advertisements;

    }

    public List<UserAdvertisement> filterAdvertisements(List<UserAdvertisement> ads, FiltersDTO filters) {
        if(filters != null) {
            ads = ads.stream()
                     .filter(a -> {
                        return (filters.getMaxBudget() == 0 || filters.getMaxBudget() >= a.getMaxBudget()) &&
                        (filters.getMaxCohabitants() == 0 || filters.getMaxCohabitants() <= a.getMaxCohabitants()) &&
                        (filters.getEntranceDate() == null || filters.getEntranceDate().isBefore(a.getEntranceDate()));
                    })
                     .toList();
        }

        return ads;
    }

}