package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.EOfferStatus;
import com.iktpreobuka.project.entities.OfferEntity;

@RestController
@RequestMapping(value = "/project/offers")
public class OfferController {

	List<OfferEntity> offers = new ArrayList<>();
	private List<OfferEntity> getDB(){
		if(offers.isEmpty()) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.DATE, 5);
			offers.add(new OfferEntity(1,"2 tickets for Killers concert", "Enjoy!!!",
					new Date(),cal.getTime(),100000.00, 6500.00, " ", 10, 0,
					EOfferStatus.WAIT_FOR_APPROVING));
			offers.add(new OfferEntity(2, "VIVAX 24LE76T2", "Don't miss this fantastic offer!",
					new Date(),cal.getTime(), 200000.00, 16500.00, " ", 5, 0,
					EOfferStatus.WAIT_FOR_APPROVING));
			offers.add(new OfferEntity(3, "Dinner for two in Aqua Doria", "Excellent offer", new
Date(), cal.getTime(), 6000.00, 3500.00, " ", 4, 0, EOfferStatus.WAIT_FOR_APPROVING));
			
		}
	return offers;
	}
	
	public OfferController() {
		offers = getDB();
	}
	
	@GetMapping
    public List<OfferEntity> getAllOffers() {
        return offers; //  lista ponuda
    }
	
	@PostMapping
	public OfferEntity addOffer(@RequestBody OfferEntity newOffer) {
	    newOffer.setId(offers.size() + 1);
	    offers.add(newOffer); 
	    return newOffer; // vraca dodatu ponudu
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<OfferEntity> updateOffer(@PathVariable Integer id, @RequestBody OfferEntity updatedOffer) {
	    for (OfferEntity offer : offers) {
	        if (offer.getId().equals(id)) {
	            
	            offer.setOfferName(updatedOffer.getOfferName());
	            offer.setOfferDescription(updatedOffer.getOfferDescription());
	            offer.setOfferCreated(updatedOffer.getOfferCreated());
	            offer.setOfferExpires(updatedOffer.getOfferExpires());
	            offer.setRegularPrice(updatedOffer.getRegularPrice());
	            offer.setActionPrice(updatedOffer.getActionPrice());
	            offer.setImagePath(updatedOffer.getImagePath());
	            offer.setAvailableOffers(updatedOffer.getAvailableOffers());
	            offer.setBoughtOffers(updatedOffer.getBoughtOffers());
	            // ne azurira offerStatus kako je navedeno u napomeni

	            return ResponseEntity.ok(offer); //vraca azuriranu ponudu
	        }
	    }
	    return ResponseEntity.notFound().build(); // vraca 404  ako ponuda nije pronadjena
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<OfferEntity> deleteOffer(@PathVariable Integer id) {
	    OfferEntity offerToDelete = null;
	    for (OfferEntity offer : offers) {
	        if (offer.getId().equals(id)) {
	            offerToDelete = offer;
	            break;
	        }
	    }
	    if (offerToDelete != null) {
	        offers.remove(offerToDelete);
	        return ResponseEntity.ok(offerToDelete); // vraca podatke o obrisanoj ponudi
	    } else {
	        return ResponseEntity.notFound().build(); // vraca 404  ako ponuda nije pronadjena
	    }
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<OfferEntity> getOfferById(@PathVariable Integer id) {
	    for (OfferEntity offer : offers) {
	        if (offer.getId().equals(id)) {
	            return ResponseEntity.ok(offer); // vraca ponudu ako je nadjena
	        }
	    }
	    return ResponseEntity.notFound().build(); // vraca 404 ako ponuda nije pronadjena
	}
	
	@PutMapping("/changeOffer/{id}/status/{status}")
	public ResponseEntity<OfferEntity> changeOfferStatus(@PathVariable Integer id, @PathVariable EOfferStatus status) {
	    for (OfferEntity offer : offers) {
	        if (offer.getId().equals(id)) {
	            offer.setOfferStatus(status); // azurira status
	            return ResponseEntity.ok(offer); // vraca azurirano
	        }
	    }
	    return ResponseEntity.notFound().build(); // vraca 404 ako ponuda nije pronadjena
	}
	
	@GetMapping("/findByPrice/{lowerPrice}/and/{upperPrice}")
	public List<OfferEntity> findOffersByPriceRange(@PathVariable Double lowerPrice, @PathVariable Double upperPrice) {
	    List<OfferEntity> filteredOffers = new ArrayList<>();
	    for (OfferEntity offer : offers) {
	        if (offer.getActionPrice() >= lowerPrice && offer.getActionPrice() <= upperPrice) {
	            filteredOffers.add(offer);
	        }
	    }
	    return filteredOffers;
	}
}
