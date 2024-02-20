package com.iktpreobuka.project.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.repositories.OfferRepository;

@Service
public class OfferServiceImpl implements OfferService{
	@Autowired
    private OfferRepository offerRepository;

	@Override
    public List<OfferEntity> getAllOffers() {
        return (List<OfferEntity>) offerRepository.findAll();
    }

    @Override
    public OfferEntity addNewOffer(OfferEntity offer) {
        return offerRepository.save(offer);
    }

    @Override
    public OfferEntity getOfferById(Integer id) throws ResourceNotFoundException {
        return offerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Offer not found for this id :: " + id));
    }

    public OfferEntity updateOffer(Integer id, OfferEntity offerDetails) {
        OfferEntity offerToUpdate = offerRepository.findById(id).orElseThrow(() ->
            new ResourceNotFoundException("Offer with id " + id + " not found."));
        
        offerToUpdate.setOfferName(offerDetails.getOfferName());
        offerToUpdate.setOfferDescription(offerDetails.getOfferDescription());
        offerToUpdate.setRegularPrice(offerDetails.getRegularPrice());
        offerToUpdate.setActionPrice(offerDetails.getActionPrice());
        offerToUpdate.setAvailableOffers(offerDetails.getAvailableOffers());
        offerToUpdate.setBoughtOffers(offerDetails.getBoughtOffers());
        offerToUpdate.setOfferStatus(offerDetails.getOfferStatus());
        

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
}
