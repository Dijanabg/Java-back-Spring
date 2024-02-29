package com.example.iktpreobuka.dataaccess.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;
import com.example.iktpreobuka.dataaccess.entities.UserEntity;
import com.example.iktpreobuka.dataaccess.repositories.AddressRepository;
import com.example.iktpreobuka.dataaccess.repositories.UserRepository;

@Service
public class UserDaoImpl implements UserDao{
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AddressRepository addressRepository;

    @Override
    public UserEntity addNewUser(String name, String lastName, String email, LocalDate dateOfBirth) {
    	if(userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email already in use.");
        }
    	UserEntity user = new UserEntity();
        user.setName(name);
        user.setLastName(lastName);
        user.setEmail(email);
        user.setDateOfBirth(dateOfBirth);
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<UserEntity> updateUser(Integer id, UserEntity userDetails) {
    	if(userDetails.getEmail() != null && userRepository.existsByEmailAndIdNot(userDetails.getEmail(), id)) {
            return ResponseEntity.badRequest().body(null); // Email je već u upotrebi
        }
    	Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        UserEntity user = userOptional.get();
        // update samo odredjenih polja, nema menjanja imena i username i passworda
        //userDetails su podaci iz body
        if (userDetails.getEmail() != null) user.setEmail(userDetails.getEmail());
        if (userDetails.getDateOfBirth() != null) user.setDateOfBirth(userDetails.getDateOfBirth());
        if (userDetails.getLastName() != null) user.setLastName(userDetails.getLastName());
        //setUserCostsBasedOnCity(user, city); // Pomoćna metoda za postavljanje troškova

        UserEntity updatedUser = userRepository.save(user);
        return ResponseEntity.ok(updatedUser);
    }
    
//    private void setUserCostsBasedOnCity(UserEntity user, String city) {
//        if ("Novi Sad".equalsIgnoreCase(city)) {
//            user.setCosts(5000);
//        } else if ("Beograd".equalsIgnoreCase(city)) {
//            user.setCosts(10000);
//        } else {
//            user.setCosts(0);
//        }
//    }

    @Override
    public Iterable<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }
    
    @Override
    public UserEntity addAddressToUser(Integer userId, Integer addressId) {
        UserEntity user = userRepository.findById(userId)
        		.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        AddressEntity address = addressRepository.findById(addressId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Address not found"));

        user.setAddress(address);
        return userRepository.save(user);
    }

    @Override
    public Optional<UserEntity> getUserById(Integer id) {
    	return userRepository.findById(id);
    }

    @Override
    public UserEntity getUserByEmail(String email) {
    	 return userRepository.findByEmail(email)
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User with email " + email + " not found"));
    }

    @Override
    public List<UserEntity> getUsersSortedByEmail() {
        // Primena Sort klase za sortiranje
        return userRepository.findAll(Sort.by(Sort.Direction.ASC, "email"));
    }

    @Override
    public List<UserEntity> getUsersByDateOfBirthSortedByName(LocalDate dob) {
        // Potrebno je implementirati metodu u repozitorijumu ako već nije
        return userRepository.findAllByDateOfBirthSortedByName(dob);
    }

    @Override
    public List<UserEntity> getUsersByFirstLetter(String letter) {
        // Implementacija zavisi od potrebe da li koristimo JPQL, Criteria API ili nešto treće
        return userRepository.findByFirstLetterOfName(letter);
    }

    @Override
    public ResponseEntity<?> removeAddressFromUser(Integer userId) {
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        UserEntity user = userOptional.get();
        user.setAddress(null);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<?> addUserToAddress(Integer addressId, Integer userId) {
        // Ova metoda bi trebala da bude obrnuto implementirana, ali je ovdje za primjer
        Optional<AddressEntity> addressOptional = addressRepository.findById(addressId);
        Optional<UserEntity> userOptional = userRepository.findById(userId);
        if (!addressOptional.isPresent() || !userOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        AddressEntity address = addressOptional.get();
        UserEntity user = userOptional.get();
        user.setAddress(address);
        userRepository.save(user);
        return ResponseEntity.ok().build();
    }
}
