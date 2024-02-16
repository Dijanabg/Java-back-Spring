package com.example.iktpreobuka.dataaccess.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.iktpreobuka.dataaccess.entities.UserEntity;


public interface UserRepository extends CrudRepository<UserEntity, Integer> {
Optional<UserEntity> findByEmail(String email);
	
	List<UserEntity> findAll(Sort sort);
	
	@Query("SELECT u FROM UserEntity u WHERE u.dateOfBirth = :dateOfBirth ORDER BY u.name ASC")
    List<UserEntity> findAllByDateOfBirthSortedByName(@Param("dateOfBirth") LocalDate dateOfBirth);
	@Query("SELECT u FROM UserEntity u WHERE u.name LIKE :letter%")
    List<UserEntity> findByFirstLetterOfName(@Param("letter") String letter);
}