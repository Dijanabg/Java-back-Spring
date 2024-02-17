package com.iktpreobuka.project.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.UserRepository;

@RestController
@RequestMapping(value = "/project/users")
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping
	public Iterable<UserEntity> getAllUsers() {
		return userRepository.findAll();
	}
	
	@PostMapping
	public UserEntity addNewUser(@RequestParam String name,@RequestParam String lastName,
	@RequestParam String email, @RequestParam String username, @RequestParam String password, @RequestParam EUserRole userRole) {
		UserEntity user = new UserEntity();
		user.setFirstName(name);
		user.setLastName(lastName);
		user.setEmail(email);
		user.setUsername(username);
		user.setPassword(password);
		user.setUserRole(userRole);
		userRepository.save(user);
		return user;
	}
	
	//vrati usera po id
	@GetMapping("/{id}")
	public ResponseEntity<UserEntity> getUserById(@PathVariable Integer id) {
		Optional<UserEntity> user = userRepository.findById(id);
		return user.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}
	
	//dodaj usera sa odredjenom rolom
	@PostMapping
	public UserEntity addUser(@RequestBody UserEntity newUser) {
		UserEntity user = new UserEntity();
	    newUser.setUserRole(EUserRole.ROLE_CUSTOMER); // postavlja role na ROLE_CUSTOMER
	    user.save(newUser); // dodaje novog korisnika u listu
	    return newUser; // vraca dodatog korisnika
	}
	
	//update usera
	@PutMapping("/{id}")
	public UserEntity updateUser(@PathVariable Integer id, @RequestBody UserEntity userUpdates) {
	    for (UserEntity user : users) {
	        if (user.getId().equals(id)) {
	            if (userUpdates.getFirstName() != null) {
	                user.setFirstName(userUpdates.getFirstName());
	            }
	            if (userUpdates.getLastName() != null) {
	                user.setLastName(userUpdates.getLastName());
	            }
	            if (userUpdates.getUsername() != null) {
	                user.setUsername(userUpdates.getUsername());
	            }
	            if (userUpdates.getEmail() != null) {
	                user.setEmail(userUpdates.getEmail());
	            }
	            // userRole i password se ne menjaju
	            return user; //azuriran  user
	        }
	    }
	    return null; // null ako ne postoji id
	}
	
	//menjanje role po Id
	@PutMapping("/change/{id}/role/{role}")
	public UserEntity changeUserRole(@PathVariable Integer id, @PathVariable String role) {
	    EUserRole newRole;
	    try {
	        newRole = EUserRole.valueOf(role.toUpperCase());
	    } catch (IllegalArgumentException e) {
	        return null; // vraca null ako prosledjena uloga nije validna
	    }

	    for (UserEntity user : users) {
	        if (user.getId().equals(id)) {
	            user.setUserRole(newRole); // menjanje role
	            return user; // izmenjen korisnik
	        }
	    }
	    return null; // null ako id ne postoji
	}
	 //menjanje passworda
	@PutMapping("/changePassword/{id}")
	public UserEntity changeUserPassword(@PathVariable Integer id, 
										@RequestParam("oldPassword") String oldPassword, 
										@RequestParam("newPassword") String newPassword) {
			for(UserEntity user : users) {
				if(user.getId().equals(id)) {
					if (user.getPassword().equals(oldPassword)) {
						user.setPassword(newPassword);//postavlja novi password
						return user;
					}else {
						return null; //ako se ne poklapa oldPassword
					}
				}
			}
			return null;
	}
	
	//delete user
	@DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Integer id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
	
	 @GetMapping("/by-username/{username}")
	  public UserEntity getUserByUsername(@PathVariable String username) {
	      for(UserEntity user : users) {
	          if(user.getUsername().equals(username)) {
	              return user;
	          }
	      }
	      return null; // null ako username ne postoji
	  }
	 
}
