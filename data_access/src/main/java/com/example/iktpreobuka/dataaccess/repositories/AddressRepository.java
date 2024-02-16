package com.example.iktpreobuka.dataaccess.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.iktpreobuka.dataaccess.entities.AddressEntity;

public interface AddressRepository extends CrudRepository<AddressEntity, Integer> {
	List<AddressEntity> findByCity(String city);
	
	@Query("SELECT a FROM AddressEntity a WHERE a.country = ?1 ORDER BY a.country ASC")
	List<AddressEntity> findByCountrySorted(String country);
}
