package com.iktpreobuka.uploadexample.repositories;


import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.uploadexample.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{
	Optional<UserEntity> findByEmail(String email);
}
