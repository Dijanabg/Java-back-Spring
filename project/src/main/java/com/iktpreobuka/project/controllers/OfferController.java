package com.iktpreobuka.project.controllers;

import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.security.Views;
import com.iktpreobuka.project.services.OfferService;


@RestController
@RequestMapping(value = "/project/offers")
public class OfferController {
	
	@Autowired
	private OfferService offerService;

	//sve ponude
    @GetMapping
    @JsonView(Views.Private.class)
    public ResponseEntity<List<OfferEntity>> getAllOffers() {
        List<OfferEntity> offers = offerService.getAllOffers();
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }
    //dodavanje offer sa kategorijom i prodavcem
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

    //azuriranje ponude
    @PutMapping("/{id}")
    public ResponseEntity<?> updateOffer(@PathVariable Integer id, @RequestBody OfferEntity offerDetails) {
            try {
                OfferEntity updatedOffer = offerService.updateOffer(id, offerDetails);
                return ResponseEntity.ok(updatedOffer);
            } catch (ResourceNotFoundException e) {
            	return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: " + e.getMessage());
            }
    }

    //ponude po id
    @GetMapping("/{id}")
    public ResponseEntity<OfferEntity> getOfferById(@PathVariable Integer id) {
        try {
            OfferEntity offer = offerService.getOfferById(id);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //brisanje ponude
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable Integer id) {
    	try {
            offerService.deleteOffer(id);
            return ResponseEntity.ok().body("Offer with ID " + id + " has been deleted successfully.");
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    //ponude po cenovnom rangu
    @GetMapping("/price-range")
    public ResponseEntity<List<OfferEntity>> findByPriceRange(@RequestParam Double lowerPrice, @RequestParam Double upperPrice) {
        List<OfferEntity> offers = offerService.findOffersByPriceRange(lowerPrice, upperPrice);
        return new ResponseEntity<>(offers, HttpStatus.OK);
    }

    //menjanje statusa ponude
    @PutMapping("/{id}/status")
    public ResponseEntity<OfferEntity> changeOfferStatus(@PathVariable Integer id, @RequestParam EOfferStatus status) {
        try {
            OfferEntity offer = offerService.changeOfferStatus(id, status);
            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
 // Metoda za azuriranje dostupnosti ponude
    @PutMapping("/{offerId}/updateAvailability")
    public ResponseEntity<?> updateOfferAvailability(@PathVariable Integer offerId, @RequestParam int quantity) {
        try {
            offerService.updateOfferAvailability(offerId, quantity);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    
    //IMAGE UPLOAD
    @PostMapping("/uploadImage/{id}")
    public ResponseEntity<?> uploadImage(@PathVariable Integer id, @RequestParam("image") MultipartFile image) {
    	final String UPLOADED_FOLDER = "C://temp//";
    	if (image.isEmpty()) {
            return new ResponseEntity<>("Please select a file to upload.", HttpStatus.BAD_REQUEST);
        }

        try {
            // Get the filename and build the local file path
            String filename = image.getOriginalFilename();
            String filePath = UPLOADED_FOLDER + filename;

            // Save the file locally
            byte[] bytes = image.getBytes();
            Path path = Paths.get(filePath);
            Files.write(path, bytes);

            // Update the offer with the path of the image
            OfferEntity offer = offerService.updateOfferImage(id, filePath);
            if (offer == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            return new ResponseEntity<>(offer, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    

}

