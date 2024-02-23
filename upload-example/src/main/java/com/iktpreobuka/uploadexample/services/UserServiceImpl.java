package com.iktpreobuka.uploadexample.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.uploadexample.entity.UserEntity;
import com.iktpreobuka.uploadexample.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	public UserRepository userRepository;
	
	@Override
	public UserEntity updateUser(Integer userId, UserEntity userDetails) throws Exception {
	    UserEntity user = userRepository.findById(userId)
	        .orElseThrow(() -> new Exception("User not found for this id :: " + userId));

	    Optional<UserEntity> existingUserByEmail = userRepository.findByEmail(userDetails.getEmail());
	    if (existingUserByEmail.isPresent() && !existingUserByEmail.get().getId().equals(user.getId())) {
	        throw new Exception("Email already in use by another user");
	    }

	    user.setName(userDetails.getName());
	    user.setEmail(userDetails.getEmail());
	    final UserEntity updatedUser = userRepository.save(user);
	    return updatedUser;
	}
	
	@Override
	public String generateCsv(List<String> fields) {
        //List<UserEntity> users = userRepository.findAll();
        List<UserEntity> users = StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
        StringBuilder sb = new StringBuilder();

        if(fields == null || fields.isEmpty()) {
            sb.append("ID,Name,Email,City,Costs\n"); // Ovo pretpostavlja da su ovo svi atributi klase UserEntity
            for(UserEntity user : users) {
                sb.append(user.getId()).append(",")
                  .append(user.getName()).append(",")
                  .append(user.getEmail()).append(",")
                  .append(user.getCity()).append(",")
                  .append(user.getCosts()).append("\n");
            }
        } else {
        	//NE ZNAMM ODUSTAJEM
            // Logika za obradu specifičnih polja koja korisnik želi
            // Ovde bi trebalo implementirati dinamičko generisanje CSV-a na osnovu `fields`
            // Može se koristiti refleksija za dinamičko pristupanje atributima ili neka druga metoda
        }

        return sb.toString();
    }
}
