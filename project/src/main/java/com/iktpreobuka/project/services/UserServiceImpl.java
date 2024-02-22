package com.iktpreobuka.project.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.EUserRole;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.exceptions.ResourceNotFoundException;
import com.iktpreobuka.project.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserEntity addNewUser(String name, String lastName, String email, String username, String password, EUserRole userRole) {
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

//	@Override
//	public Optional<UserEntity> getUserById(Integer id) {
//		return userRepository.findById(id);
//		//bez obrade izuzetaka
//	}
	@Override
    public UserEntity getUserById(Integer id) {
        return userRepository.findById(id).orElseThrow(() -> 
            new ResourceNotFoundException("User not found for this id :: " + id));
    }
	@Override
	public List<UserEntity> getAllUsers() {
		return (List<UserEntity>) userRepository.findAll();
	}

	@Override
	public UserEntity updateUser(Integer id, UserEntity userUpdates) {
		Optional<UserEntity> userOptional = userRepository.findById(id);
	    if (userOptional.isPresent()) {
	        UserEntity user = userOptional.get();
	        if (userUpdates.getLastName() != null) {
	            user.setLastName(userUpdates.getLastName());
	        }
	        if (userUpdates.getUsername() != null) {
	            user.setUsername(userUpdates.getUsername());
	        }
	        if (userUpdates.getEmail() != null) {
	            user.setEmail(userUpdates.getEmail());
	        }
	        // ažuriranje ostalih polja ako je potrebno
	        
	       // čuva izmenjenog korisnika u bazi
	        userRepository.save(user);
	        
	        // vraća ažuriranog korisnika
	        return user;
	    } else {
	        // null ili baca izuzetak koje smo "bas radili", ako korisnik sa datim ID-jem ne postoji
	        return null;
	    }
	}

	@Override
	public boolean deleteUser(Integer id) {
		if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
	}

	@Override
	public UserEntity changeUserRole(Integer id, EUserRole newRole) {
	    Optional<UserEntity> userOptional = userRepository.findById(id);
	    if (userOptional.isPresent()) {
	        UserEntity user = userOptional.get();
	        user.setUserRole(newRole); // nova uloga
	        userRepository.save(user); //save izmena u bazi
	        return user; // vraća ažuriranog korisnika
	    } else {
	        return null; // vraća null ako korisnik sa datim id ne postoji
	    }
	}

	@Override
	public UserEntity changeUserPassword(Integer id, String oldPassword, String newPassword) {
	    Optional<UserEntity> userOptional = userRepository.findById(id);
	    if (userOptional.isPresent()) {
	        UserEntity user = userOptional.get();
	        if (user.getPassword().equals(oldPassword)) {
	            user.setPassword(newPassword); // Postavljanje nove lozinke direktno
	            userRepository.save(user);
	            return user;
	        } else {
	            // Lozinka se ne poklapa sa starom lozinkom
	            return null;
	        }
	    }
	    // Korisnik nije pronađen
	    return null;
	}

	@Override
	public UserEntity getUserByUsername(String username) {
	    return userRepository.findByUsername(username).orElse(null);
	}
	@Override
	public boolean isSeller(Integer userId) {
        UserEntity user = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        return user.getUserRole() == EUserRole.ROLE_SELLER;
    }
	
}
