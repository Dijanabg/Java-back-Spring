package com.iktpreobuka.uploadexample.contollers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.iktpreobuka.uploadexample.entity.UserEntity;
import com.iktpreobuka.uploadexample.services.UserService;

public class UserController {
	
	@Autowired
	public UserService userService;
	
	@PutMapping("/users/{id}")
	public ResponseEntity<?> updateUser(@PathVariable Integer id, @RequestBody UserEntity userDetails) {
	    try {
	        UserEntity updatedUser = userService.updateUser(id, userDetails);
	        return ResponseEntity.ok(updatedUser);
	    } catch (Exception e) {
	        // ako korisnik nije pronađen ili email već postoji
	        return ResponseEntity.badRequest().body(e.getMessage());
	    }
	}
}
