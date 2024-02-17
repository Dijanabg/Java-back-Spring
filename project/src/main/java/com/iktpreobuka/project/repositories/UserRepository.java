package com.iktpreobuka.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.project.entities.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Integer>{

}
