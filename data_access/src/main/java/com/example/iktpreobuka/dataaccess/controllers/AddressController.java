package com.example.iktpreobuka.dataaccess.controllers;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.repositories.AddressRepository;


@RestController
@RequestMapping(path = "/api/v1/addresses")
public class AddressController {

	@Autowired
	private AddressRepository addressRepository;
	
	@RequestMapping(method = RequestMethod.POST)
	public AddressEntity addNewAddress(@RequestParam String street, @RequestParam String city,
	@RequestParam String country) {
		AddressEntity address = new AddressEntity();
		address.setStreet(street);
		address.setCity(city);
		address.setCountry(country);
		addressRepository.save(address);
		return address;
	}
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<AddressEntity> getAllAddresses() {
		return addressRepository.findAll();
	}
	
	//vraca adresu po id
	@GetMapping("/{id}")
	public ResponseEntity<AddressEntity> getAddressById(@PathVariable Integer id) {
	    Optional<AddressEntity> address = addressRepository.findById(id);
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
	    Optional<AddressEntity> addressOptional = addressRepository.findById(id);
	    if(addressOptional.isPresent()) {
	        AddressEntity address = addressOptional.get();
	        address.setStreet(addressDetails.getStreet());
	        address.setCity(addressDetails.getCity());
	        address.setCountry(addressDetails.getCountry());
	        AddressEntity updatedAddress = addressRepository.save(address);
	        return ResponseEntity.ok(updatedAddress);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	//delete po id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAddress(@PathVariable Integer id) {
	    Optional<AddressEntity> address = addressRepository.findById(id);
	    if(address.isPresent()) {
	        addressRepository.delete(address.get());
	        return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@GetMapping("/by-city")
    public List<AddressEntity> getAddressesByCity(@RequestParam String city) {
        return addressRepository.findByCity(city);
    }
	
	@GetMapping("/by-country")
    public List<AddressEntity> getAddressesByCountrySorted(@RequestParam String country) {
        return addressRepository.findByCountrySorted(country);
    }
}

