package com.iktpreobuka.project.services;

import java.util.List;

import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;

public interface OfferService {
	List<OfferEntity> getAllOffers();
    OfferEntity addNewOffer(OfferEntity offer);
    OfferEntity getOfferById(Integer id) throws ResourceNotFoundException;
    OfferEntity updateOffer(Integer id, OfferEntity offerUpdates) throws ResourceNotFoundException;
    boolean deleteOffer(Integer id) throws ResourceNotFoundException;
    List<OfferEntity> findOffersByPriceRange(Double lowerPrice, Double upperPrice);
    OfferEntity changeOfferStatus(Integer id, EOfferStatus newStatus) throws ResourceNotFoundException;
}


