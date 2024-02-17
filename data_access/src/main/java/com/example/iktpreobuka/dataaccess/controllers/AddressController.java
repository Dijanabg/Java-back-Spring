package com.example.iktpreobuka.dataaccess.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.services.AddressDaoImpl;


@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {
	
	@Autowired
	private AddressDaoImpl addressDaoImpl;
	
	@GetMapping("/users/{name}")
	public List<AddressEntity> getAddressForAUser(@PathVariable String name) {
	return addressDaoImpl.findAddressesByUserName(name);
	}
	
	@PostMapping
	public AddressEntity addNewAddress(@RequestParam String street, @RequestParam String city,
	@RequestParam String country) {
		return addressDaoImpl.addNewAddress(street, city, country);
	}
	@GetMapping
	public Iterable<AddressEntity> getAllAddresses() {
		return addressDaoImpl.findAll();
	}
	
	//vraca adresu po id
	@GetMapping("/{id}")
	public ResponseEntity<AddressEntity> getAddressById(@PathVariable Integer id) {
	    Optional<AddressEntity> address = addressDaoImpl.findById(id);
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
            AddressEntity updatedAddress = addressDaoImpl.updateAddress(id, addressDetails);
            return ResponseEntity.ok(updatedAddress);
        } catch (RuntimeException ex) {
            return ResponseEntity.notFound().build();
        }
	}
	
	//delete po id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
		addressDaoImpl.deleteAddress(id);
        return ResponseEntity.ok().build();
	}
	
	@GetMapping("/by-city")
    public List<AddressEntity> getAddressesByCity(@RequestParam String city) {
        return addressDaoImpl.findByCity(city);
    }
	
	@GetMapping("/by-country")
    public List<AddressEntity> getAddressesByCountrySorted(@RequestParam String country) {
		return addressDaoImpl.findByCountrySorted(country);
    }
}

