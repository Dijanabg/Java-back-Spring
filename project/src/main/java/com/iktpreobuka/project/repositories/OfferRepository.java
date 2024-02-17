package com.iktpreobuka.project.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.iktpreobuka.project.entities.OfferEntity;

@Repository
public interface OfferRepository extends CrudRepository<OfferEntity, Integer>{

}
