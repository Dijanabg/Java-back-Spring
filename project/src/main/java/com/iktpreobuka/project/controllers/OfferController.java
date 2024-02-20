package com.iktpreobuka.project.controllers;

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
import com.iktpreobuka.project.services.OfferServiceImpl;

@RestController
@RequestMapping(value = "/project/offers")
public class OfferController {
	@Autowired
	private OfferServiceImpl offerServiceImpl;


    @GetMapping
    public ResponseEntity<List<OfferEntity>> getAllOffers() {
        List<OfferEntity> offers = offerServiceImpl.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OfferEntity> createOffer(@RequestBody OfferEntity offer) {
        OfferEntity savedOffer = offerServiceImpl.addNewOffer(offer);
        return new ResponseEntity<>(savedOffer, HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offerDetails) {
            try {
                OfferEntity updatedOffer = offerServiceImpl.updateOffer(id, offerDetails);
                return ResponseEntity.ok(updatedOffer);
            } catch (ResourceNotFoundException e) {
                return ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body(null); // Možete prilagoditi telo odgovora za grešku ako želite
            }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OfferEntity> getOfferById(@PathVariable Integer id) {
        try {
            OfferEntity offer = offerServiceImpl.getOfferById(id);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Integer id) {
    	try {
            offerServiceImpl.deleteOffer(id);
            return ResponseEntity.ok().body("Offer with ID " + id + " has been deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/price-range")
    public ResponseEntity<List<OfferEntity>> findByPriceRange(@RequestParam Double lowerPrice, @RequestParam Double upperPrice) {
        List<OfferEntity> offers = offerServiceImpl.findOffersByPriceRange(lowerPrice, upperPrice);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OfferEntity> changeOfferStatus(@PathVariable Integer id, @RequestParam EOfferStatus status) {
        try {
            OfferEntity offer = offerServiceImpl.changeOfferStatus(id, status);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
