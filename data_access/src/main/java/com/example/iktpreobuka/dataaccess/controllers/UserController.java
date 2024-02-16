package com.example.iktpreobuka.dataaccess.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.entities.UserEntity;
import com.example.iktpreobuka.dataaccess.repositories.AddressRepository;
import com.example.iktpreobuka.dataaccess.repositories.UserRepository;


@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;
	
	
	@RequestMapping(method = RequestMethod.POST)
	public UserEntity addNewUser(@RequestParam String name,@RequestParam String lastName,
	@RequestParam String email, @RequestParam LocalDate dateOfBirth) {
		UserEntity user = new UserEntity();
		user.setName(name);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setDateOfBirth(dateOfBirth);
		userRepository.save(user);
		return user;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public Iterable<UserEntity> getAllUsers() {
	return userRepository.findAll();
	}
	
	@RequestMapping(method = RequestMethod.PUT, value = "/{id}/address")
	public UserEntity addAddressToAUser(@PathVariable Integer id,
	@RequestParam Integer address) {
		UserEntity user = userRepository.findById(id).get();
		AddressEntity adr = addressRepository.findById(address).get();
		user.setAddress(adr);
		userRepository.save(user); // automatski ce biti sacuvana i adresa
		return user;
	}
	//vracanje korisnika po id
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
		Optional<UserEntity> user = userRepository.findById(id);
		if (user.isPresent()) {
		    return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
		    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}
}
		
		// pronalazenje korisnika po emailu
	@GetMapping("/by-email")
	public UserEntity getUserByEmail(@RequestParam String email) {
	        Optional<UserEntity> user = userRepository.findByEmail(email);
	        if(user.isPresent()) {
	            return user.get();
	        } else {
	            // Ovde možete vratiti prilagođeni odgovor ili baciti izuzetak ukoliko korisnik nije pronađen
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found");
	        }
	}
		
	// pronalazenje korisnika po imenu i sortiranje po emailu
	@GetMapping("/by-name")
	public List<UserEntity> getUsersSortedByEmail() {
	        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
	}
	    
	//ne kontam ovaj zadatak, treba da vrati sve sa istim datumom rodjenja sortirane prema imenu, ako sam dobro shvatila?!
	@GetMapping("/by-dob")
	public List<UserEntity> getUsersByDateOfBirthSortedByName(
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob) {
	        return userRepository.findAllByDateOfBirthSortedByName(dob);
	}
	    
	 @GetMapping("/by-name-first-letter/{letter}")
	 public List<UserEntity> getUsersByFirstLetter(@PathVariable String letter) {
	        return userRepository.findByFirstLetterOfName(letter);
	 }
	    
	 //uklanjanje adrese iz korisnika
	 @DeleteMapping("/{userId}/address")
	 public ResponseEntity<?> removeAddressFromUser(@PathVariable Integer userId) {
	        Optional<UserEntity> userOptional = userRepository.findById(userId);
	        if (!userOptional.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        UserEntity user = userOptional.get();
	        user.setAddress(null); // Pretpostavljamo da je `setAddress` metoda za postavljanje adrese na null
	        userRepository.save(user);
	        return ResponseEntity.ok().build();
	 }
	    /*dodati REST entpoint u UserController koji omogućava
	    prosleđivanje parametara za kreiranje korisnika i adrese
	    • kreira korisnika
	    • proverava postojanje adrese
	    • ukoliko adresa postoji u bazi podataka dodaje je korisniku
	    • ukoliko adresa ne postoji, kreira adresu i dodaje je korisniku*/
	    
	 
	 //nisam bas skapirala ovaj deo
	    
	    @PostMapping("/{addressId}/user/{userId}")
	    public ResponseEntity<?> addUserToAddress(@PathVariable Integer addressId, @PathVariable Integer userId) {
	        Optional<AddressEntity> addressOptional = addressRepository.findById(addressId);
	        Optional<UserEntity> userOptional = userRepository.findById(userId);
	        if (!addressOptional.isPresent() || !userOptional.isPresent()) {
	            return ResponseEntity.notFound().build();
	        }
	        AddressEntity address = addressOptional.get();
	        UserEntity user = userOptional.get();
	        user.setAddress(address); // DodajeMO adresu korisniku
	        userRepository.save(user);
	        return ResponseEntity.ok().build();
	    }
}
