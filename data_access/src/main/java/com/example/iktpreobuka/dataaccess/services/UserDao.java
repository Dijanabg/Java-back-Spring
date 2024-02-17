package com.example.iktpreobuka.dataaccess.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;

import com.example.iktpreobuka.dataaccess.entities.UserEntity;

public interface UserDao {
	UserEntity addNewUser(String name, String lastName, String email, LocalDate dateOfBirth);
    ResponseEntity<UserEntity> updateUser(Integer id, UserEntity userDetails);
    Iterable<UserEntity> getAllUsers();
    UserEntity addAddressToUser(Integer userId, Integer addressId);
    Optional<UserEntity> getUserById(Integer id);
    UserEntity getUserByEmail(String email);
    List<UserEntity> getUsersSortedByEmail();
    List<UserEntity> getUsersByDateOfBirthSortedByName(LocalDate dob);
    List<UserEntity> getUsersByFirstLetter(String letter);
    ResponseEntity<?> removeAddressFromUser(Integer userId);
    ResponseEntity<?> addUserToAddress(Integer addressId, Integer userId);
}
