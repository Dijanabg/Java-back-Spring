package com.iktpreobuka.project.controllers;


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

import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.security.Views;
import com.iktpreobuka.project.services.UserService;


@RestController
@RequestMapping(value = "/project/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private UserService userService;
	
	@GetMapping("/public")
    @JsonView(Views.Public.class)
    public ResponseEntity<List<UserEntity>> getUsersPublic() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/private")
    @JsonView(Views.Private.class)
    public ResponseEntity<List<UserEntity>> getUsersPrivate() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/admin")
    @JsonView(Views.Admin.class)
    public ResponseEntity<List<UserEntity>> getUsersAdmin() {
        List<UserEntity> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
	//dodaj novog usera
	@PostMapping
	public UserEntity addNewUser(@RequestBody UserEntity newUser) {
        return userService.addNewUser(newUser.getFirstName(), newUser.getLastName(), newUser.getEmail(),
                newUser.getUsername(), newUser.getPassword(), newUser.getUserRole());
    }
	
	//vrati usera po id
//	@GetMapping("/{id}")
//	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
//		Optional<UserEntity> user = userServiceImpl.getUserById(id);
//        return user.map(ResponseEntity::ok)
//                   .orElseGet(() -> ResponseEntity.notFound().build());
//	}
	//sa hvatanjem izuzetka
	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
	    try {
	        UserEntity user = userService.getUserById(id);
	        return new ResponseEntity<>(user, HttpStatus.OK);
	    } catch (ResourceNotFoundException e) {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	
	//vrati sve usere
	@GetMapping
	public Iterable<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	//update usera
	@PutMapping("/{id}")
	public ResponseEntity<UserEntity> updateUser(@PathVariable Integer id, @RequestBody UserEntity userUpdates) {
	    UserEntity updatedUser = userService.updateUser(id, userUpdates);
	    if (updatedUser != null) {
	        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	    } else {
	        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
	}
	//delete user
	@DeleteMapping("/{id}")
    public boolean deleteUser(@PathVariable Integer id) {
//        if (userRepository.existsById(id)) {
//            userRepository.deleteById(id);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
		return userService.deleteUser(id);
    }
	//menjanje role po Id
	@PutMapping("/change/{id}/role/{role}")
    public ResponseEntity<UserEntity> changeUserRole(@PathVariable Integer id, @PathVariable String role) {
        EUserRole newRole;
        try {
            newRole = EUserRole.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            // uloga nije validna, vraća BAD_REQUEST
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserEntity updatedUser = userService.changeUserRole(id, newRole);
        if (updatedUser != null) {
            //korisnik uspešno ažuriran, vraća ažuriranog korisnika
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } else {
            // korisnik sa datim ID-jem ne postoji, vraća NOT_FOUND
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
	}

	//menjanje passworda
	@PutMapping("/changePassword/{id}")
	public ResponseEntity<?> changeUserPassword(@PathVariable Integer id, 
	                                            @RequestParam("oldPassword") String oldPassword, 
	                                            @RequestParam("newPassword") String newPassword) {
	    UserEntity updatedUser = userService.changeUserPassword(id, oldPassword, newPassword);
	    if (updatedUser != null) {
	        return ResponseEntity.ok().build();
	    } else {
	        return ResponseEntity.badRequest().body("Invalid old password or user not found.");
	    }
	}

//	//vrati usera po username
	@GetMapping("/by-username/{username}")
	public ResponseEntity<UserEntity> getUserByUsername(@PathVariable String username) {
	    UserEntity user = userService.getUserByUsername(username);
	    if (user != null) {
	        return ResponseEntity.ok(user);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

}
