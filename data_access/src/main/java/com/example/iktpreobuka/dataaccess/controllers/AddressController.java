package com.example.iktpreobuka.dataaccess.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.services.AddressDao;



@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {
	
	@Autowired
	private AddressDao addressDao;
	
	@GetMapping("/users/{name}")
	public List<AddressEntity> getAddressForAUser(@PathVariable String name) {
	return addressDao.findAddressesByUserName(name);
	}
	
	@PostMapping
	public AddressEntity addNewAddress(@RequestParam String street, @RequestParam String city,
	@RequestParam String country) {
		return addressDao.addNewAddress(street, city, country);
	}
	@GetMapping
	public Iterable<AddressEntity> getAllAddresses() {
		return addressDao.findAll();
	}
	
	//vraca adresu po id
	@GetMapping("/{id}")
	public ResponseEntity<AddressEntity> getAddressById(@PathVariable Integer id) {
	    Optional<AddressEntity> address = addressDao.findById(id);
	    if(address.isPresent()) {
	        return ResponseEntity.ok(address.get());
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//azuriranje adrese
	@PutMapping("/{id}")
	public ResponseEntity<AddressEntity> updateAddress(@PathVariable Integer id,
	                                                   @RequestBody AddressEntity addressDetails) {
		try {
            AddressEntity updatedAddress = addressDao.updateAddress(id, addressDetails);
            return ResponseEntity.ok(updatedAddress);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
	}
	
	//delete po id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
		addressDao.deleteAddress(id);
        return ResponseEntity.ok().build();
	}
	
	@GetMapping("/by-city")
    public List<AddressEntity> getAddressesByCity(@RequestParam String city) {
        return addressDao.findByCity(city);
    }
	
	@GetMapping("/by-country")
    public List<AddressEntity> getAddressesByCountrySorted(@RequestParam String country) {
		return addressDao.findByCountrySorted(country);
    }
	// Endpoint za dodavanje korisnika u adresu
    @PostMapping("/{addressId}/addUser/{userId}")
    public ResponseEntity<?> addUserToAddress(@PathVariable Integer addressId, @PathVariable Integer userId) {
        try {
            addressDao.addUserToAddress(addressId, userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    // Endpoint za uklanjanje korisnika iz adrese
    @PutMapping("/removeUser/{userId}")
    public ResponseEntity<?> removeUserFromAddress(@PathVariable Integer userId) {
        try {
            addressDao.removeUserFromAddress(userId);
            return ResponseEntity.ok().build();
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }
}

