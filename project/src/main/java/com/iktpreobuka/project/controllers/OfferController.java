package com.iktpreobuka.project.controllers;

import java.nio.file.AccessDeniedException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.services.OfferService;


@RestController
@RequestMapping(value = "/project/offers")
public class OfferController {
	@Autowired
	private OfferService offerService;


    @GetMapping
    public ResponseEntity<List<OfferEntity>> getAllOffers() {
        List<OfferEntity> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping("/{categoryId}/seller/{sellerId}")
    public ResponseEntity<?> addOfferWithCategoryAndSeller(@PathVariable Integer categoryId, @PathVariable Integer sellerId, @RequestBody OfferEntity offerDetails) throws AccessDeniedException {
    	try {
            OfferEntity newOffer = offerService.addOfferWithCategoryAndSeller(categoryId, sellerId, offerDetails);
            return new ResponseEntity<>(newOffer, HttpStatus.CREATED);
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @PutMapping("/{id}/category/{categoryId}")
    public ResponseEntity<?> updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offerDetails) {
            try {
                OfferEntity updatedOffer = offerService.updateOffer(id, offerDetails);
                return ResponseEntity.ok(updatedOffer);
            } catch (ResourceNotFoundException e) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
            }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferEntity> getOfferById(@PathVariable Integer id) {
        try {
            OfferEntity offer = offerService.getOfferById(id);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Integer id) {
    	try {
            offerService.deleteOffer(id);
            return ResponseEntity.ok().body("Offer with ID " + id + " has been deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<OfferEntity>> findByPriceRange(@RequestParam Double lowerPrice, @RequestParam Double upperPrice) {
        List<OfferEntity> offers = offerService.findOffersByPriceRange(lowerPrice, upperPrice);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OfferEntity> changeOfferStatus(@PathVariable Integer id, @RequestParam EOfferStatus status) {
        try {
            OfferEntity offer = offerService.changeOfferStatus(id, status);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

