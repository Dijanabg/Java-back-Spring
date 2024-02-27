package com.iktpreobuka.project.services;

import java.nio.file.AccessDeniedException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.CategoryEntity;
import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.repositories.CategoryRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class OfferServiceImpl implements OfferService{
	@Autowired
    private OfferRepository offerRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;
    
	@Override
    public List<OfferEntity> getAllOffers() {
        return (List<OfferEntity>) offerRepository.findAll();
    }

    @Override
    public OfferEntity addNewOffer(OfferEntity offer, Integer userId, Integer categoryId) {
    	 UserEntity user = userRepository.findById(userId)
    		        .orElseThrow(() -> new RuntimeException("User not found"));
    		    CategoryEntity category = categoryRepository.findById(categoryId)
    		        .orElseThrow(() -> new RuntimeException("Category not found"));

    		    // Provjera da li korisnik ima ulogu ROLE_SELLER
    		    if (user.getUserRole() != EUserRole.ROLE_SELLER) {
    		        throw new RuntimeException("Only users with ROLE_SELLER can create offers.");
    		    }
    		   
    		    // Postavljanje kategorije i korisnika za ponudu
    		    offer.setCategory(category);
    		    offer.setUser(user);
    		    
    		    // Postavljanje datuma kreiranja i isteka ponude
    		    LocalDateTime today = LocalDateTime.now();
    		    offer.setOfferCreated(today);
    		    offer.setOfferExpires(today.plusDays(10));

    		    return offerRepository.save(offer);
    }

    @Override
    public OfferEntity getOfferById(Integer id) throws ResourceNotFoundException {
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this id :: " + id));
    }

    @Override
    public OfferEntity updateOffer(Integer id, OfferEntity offerDetails) {
        OfferEntity offerToUpdate = offerRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Offer with id " + id + " not found."));
        
        if (offerDetails.getOfferName() != null && !offerDetails.getOfferName().isEmpty()) {
            offerToUpdate.setOfferName(offerDetails.getOfferName());
        }

        if (offerDetails.getOfferDescription() != null && !offerDetails.getOfferDescription().isEmpty()) {
            offerToUpdate.setOfferDescription(offerDetails.getOfferDescription());
        }

        if (offerDetails.getRegularPrice() != null && offerDetails.getRegularPrice() > 0) {
            offerToUpdate.setRegularPrice(offerDetails.getRegularPrice());
        }

        if (offerDetails.getActionPrice() != null && offerDetails.getActionPrice() > 0 && offerDetails.getActionPrice() < offerToUpdate.getRegularPrice()) {
            offerToUpdate.setActionPrice(offerDetails.getActionPrice());
        }

        if (offerDetails.getAvailableOffers() != null && offerDetails.getAvailableOffers() >= 0) {
            offerToUpdate.setAvailableOffers(offerDetails.getAvailableOffers());
        }

        if (offerDetails.getBoughtOffers() != null && offerDetails.getBoughtOffers() >= 0) {
            offerToUpdate.setBoughtOffers(offerDetails.getBoughtOffers());
        }
        return offerRepository.save(offerToUpdate);
    }

    @Override
    public boolean deleteOffer(Integer id) throws ResourceNotFoundException {
        OfferEntity existingOffer = getOfferById(id);
        offerRepository.delete(existingOffer);
        return true;
    }

    @Override
    public List<OfferEntity> findOffersByPriceRange(Double lowerPrice, Double upperPrice) {
    	 Iterable<OfferEntity> offersIterable = offerRepository.findAll();
    	    List<OfferEntity> filteredOffers = new ArrayList<>();
    	    
    	    offersIterable.forEach(offer -> {
    	        if (offer.getActionPrice() >= lowerPrice && offer.getActionPrice() <= upperPrice) {
    	            filteredOffers.add(offer);
    	        }
    	    });
    	    return filteredOffers;
    }

    @Override
    public OfferEntity changeOfferStatus(Integer id, EOfferStatus newStatus) throws ResourceNotFoundException {
        OfferEntity existingOffer = getOfferById(id);
        
        existingOffer.setOfferStatus(newStatus);
        return offerRepository.save(existingOffer);
    }
    @Override
    public OfferEntity addOfferWithCategoryAndSeller(Integer categoryId, Integer sellerId, OfferEntity offerDetails) throws AccessDeniedException {
        UserEntity seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new RuntimeException("Seller not found."));
        if (!seller.getUserRole().equals(EUserRole.ROLE_SELLER)) {
            throw new AccessDeniedException("Only users with ROLE_SELLER can create offers.");
        }

        CategoryEntity category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found."));

        offerDetails.setUser(seller);
        offerDetails.setCategory(category);
        offerDetails.setOfferCreated(LocalDateTime.now());
        offerDetails.setOfferExpires(LocalDateTime.now().plusDays(10));

        return offerRepository.save(offerDetails);
    }
    
    @Override
 // Metoda za izmenu broja kupljenih/dostupnih ponuda
    public OfferEntity updateOfferAvailability(Integer offerId, Integer boughtAmount) {
        Optional<OfferEntity> offerOptional = offerRepository.findById(offerId);
        if (offerOptional.isPresent()) {
            OfferEntity offer = offerOptional.get();
            // Pretpostavimo da imate settere i gettere za boughtOffers i availableOffers
            int currentBought = offer.getBoughtOffers();
            int currentAvailable = offer.getAvailableOffers();

            // Ažuriranje broja kupljenih i dostupnih ponuda
            offer.setBoughtOffers(currentBought + boughtAmount);
            offer.setAvailableOffers(currentAvailable - boughtAmount);

            // Provera da li je broj dostupnih ponuda manji od nule
            if (offer.getAvailableOffers() < 0) {
                throw new RuntimeException("There are not enough available offers for this operation.");
            }

            // Sačuvaj izmenjenu ponudu
            return offerRepository.save(offer);
        } else {
            throw new EntityNotFoundException("Offer with ID " + offerId + " was not found.");
        }
    }
    @Override
    public boolean existsActiveOffersForCategory(Integer categoryId) {
        return offerRepository.countByCategory_IdAndOfferExpiresAfter(categoryId, LocalDate.now()) > 0;
    }
    
    @Override
    //UPDATE iMAGE
    public OfferEntity updateOfferImage(Integer id, String imagePath) {
        Optional<OfferEntity> offerOpt = offerRepository.findById(id);
        if (!offerOpt.isPresent()) {
            return null;
        }
        OfferEntity offer = offerOpt.get();
        offer.setImagePath(imagePath); // Pretpostavljamo da OfferEntity ima polje imagePath
        offerRepository.save(offer);
        return offer;
    }
    
//    public void markExpiredOffers() {
//    	List<OfferEntity> offers = StreamSupport.stream(offerRepository.findAll().spliterator(), false)
//                .collect(Collectors.toList());
//        LocalDate today = LocalDate.now();
//        offers.forEach(offer -> {
//            if (offer.getOfferExpires().isBefore(today) && !offer.isExpired()) {
//                offer.setExpired(true);//zaboravim da nemam polje true false 
//                offerRepository.save(offer);
//                billService.cancelBillsForExpiredOffer(offer.getId());
//            }
//        });
//    }
}
