package com.iktpreobuka.project.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.UserEntity;

@RestController
@RequestMapping(value = "/project/users")
public class UserController {
	
	List<UserEntity> users = new ArrayList<>();
	private List<UserEntity> getDB() {
		if(users.isEmpty()) {
			users.add(new UserEntity(1, "Vladimir", "Dimitrieski", "dimitrieski@uns.ac.rs", "vladimir", "vladimir", EUserRole.ROLE_CUSTOMER));
			users.add(new UserEntity(2, "Milan", "Celikovic", "milancel@uns.ac.rs", "milan", "milan", EUserRole.ROLE_CUSTOMER));
			users.add(new UserEntity(3, "Nebojsa", "Horvat", "horva.n@uns.ac.rs", "nebojsa", "nebojsa", EUserRole.ROLE_CUSTOMER));
		}
	return users;
	}
	public UserController() {
	        users = getDB(); 
	}
	
	//vrati sve usere
	@GetMapping
    public List<UserEntity> getAllUsers() {
        return users;
    }
	
	//vrati usera po id
	@GetMapping("/{id}")
	public UserEntity getUserById(@PathVariable Integer id) {
	    for (UserEntity user : users) {
	        if (user.getId().equals(id)) {
	            return user;
	        }
	    }
	    return null; // vraca null ako korisnik  ne postoji
	}
	
	//dodaj usera sa odredjenom rolom
	@PostMapping
	public UserEntity addUser(@RequestBody UserEntity newUser) {
	    newUser.setUserRole(EUserRole.ROLE_CUSTOMER); // postavlja role na ROLE_CUSTOMER
	    users.add(newUser); // dodaje novog korisnika u listu
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
	public UserEntity deleteUser(@PathVariable Integer id) {
	    for (Iterator<UserEntity> iterator = users.iterator(); iterator.hasNext();) {
	        UserEntity user = iterator.next();
	        if (user.getId().equals(id)) {
	            iterator.remove(); // brise usera iz liste
	            return user; // vraca obrisanog usera
	        }
	    }
	    return null; // null ako id ne postoji
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
