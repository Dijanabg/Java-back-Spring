package com.iktpreobuka.project.services;

import java.nio.file.AccessDeniedException;
import java.util.List;

import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;

public interface OfferService {
	List<OfferEntity> getAllOffers();
    OfferEntity addNewOffer(OfferEntity offer, Integer userId, Integer categoryId);
    OfferEntity getOfferById(Integer id) throws ResourceNotFoundException;
    OfferEntity updateOffer(Integer id, OfferEntity offerUpdates) throws ResourceNotFoundException;
    boolean deleteOffer(Integer id) throws ResourceNotFoundException;
    List<OfferEntity> findOffersByPriceRange(Double lowerPrice, Double upperPrice);
    OfferEntity changeOfferStatus(Integer id, EOfferStatus newStatus) throws ResourceNotFoundException;
    OfferEntity addOfferWithCategoryAndSeller(Integer categoryId, Integer sellerId, OfferEntity offerDetails) throws AccessDeniedException;
}


