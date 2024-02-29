package com.example.iktpreobuka.dataaccess.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.example.iktpreobuka.dataaccess.entities.UserEntity;
import com.example.iktpreobuka.dataaccess.repositories.UserRepository;
import com.example.iktpreobuka.dataaccess.services.UserDao;


@RestController
@RequestMapping(path = "/api/v1/users")
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@PostMapping
	public ResponseEntity<UserEntity> addNewUser(@RequestParam String name,@RequestParam String lastName,
	@RequestParam String email, @RequestParam LocalDate dateOfBirth) {
		try {
		UserEntity user = userDao.addNewUser(name, lastName, email, dateOfBirth);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
		}catch (IllegalArgumentException e) {
        return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
    }
	
	//update
	@PutMapping("/{id}")
    public ResponseEntity<UserEntity> updateUser(@PathVariable Integer id, @RequestBody UserEntity userDetails) {
		return userDao.updateUser(id, userDetails);
    }
	
    @GetMapping
    public ResponseEntity<Iterable<UserEntity>> getAllUsers() {
        Iterable<UserEntity> users = userDao.getAllUsers();
        if (!users.iterator().hasNext()) { // Provera da li je lista prazna
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

	
    @PutMapping("/{id}/address")
    public ResponseEntity<UserEntity> addAddressToUser(@PathVariable Integer id, @RequestParam Integer addressId) {
    	UserEntity updatedUser = userDao.addAddressToUser(id, addressId);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }
	//vracanje korisnika po id
	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
		Optional<UserEntity> user = userDao.getUserById(id);
        return user.map(ResponseEntity::ok)
                   .orElseGet(() -> ResponseEntity.notFound().build());
	}
		
		// pronalazenje korisnika po emailu
	@GetMapping("/by-email")
	public ResponseEntity<UserEntity> getUserByEmail(@RequestParam String email) {
        UserEntity user = userDao.getUserByEmail(email);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
		
	// pronalazenje korisnika po imenu i sortiranje po emailu
	@GetMapping("/by-name")
	public List<UserEntity> getUsersSortedByEmail() {
	        return userDao.getUsersSortedByEmail();
	}
	    
	//ne kontam ovaj zadatak, treba da vrati sve sa istim datumom rodjenja sortirane prema imenu, ako sam dobro shvatila?!
	@GetMapping("/by-dob")
	public List<UserEntity> getUsersByDateOfBirthSortedByName(
	            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dob) {
	        return userDao.getUsersByDateOfBirthSortedByName(dob);
	}
	    
	 @GetMapping("/by-name-first-letter/{letter}")
	 public List<UserEntity> getUsersByFirstLetter(@PathVariable String letter) {
	        return userDao.getUsersByFirstLetter(letter);
	 }
	    
	 //uklanjanje adrese iz korisnika
	 @DeleteMapping("/{userId}/address")
	 public ResponseEntity<?> removeAddressFromUser(@PathVariable Integer userId) {
		 return userDao.removeAddressFromUser(userId);
	 }
	 
	    
	 @PostMapping("/{addressId}/user/{userId}")
	 public ResponseEntity<?> addUserToAddress(@PathVariable Integer addressId, @PathVariable Integer userId) {
		 return userDao.addUserToAddress(addressId, userId);
	 }
}
