package com.iktpreobuka.project.repositories;

import java.time.LocalDate;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.project.entities.OfferEntity;

@Repository
public interface OfferRepository extends CrudRepository<OfferEntity, Integer>{
	Integer countByCategory_IdAndOfferExpiresAfter(Integer categoryId, LocalDate date);
}
