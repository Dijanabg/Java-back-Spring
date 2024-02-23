package com.iktpreobuka.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.iktpreobuka.project.entities.VoucherEntity;

public interface VoucherRepository extends CrudRepository<VoucherEntity, Integer> {
	List<VoucherEntity> findByUserId(Integer userId);
    List<VoucherEntity> findByOfferId(Integer offerId);
    List<VoucherEntity> findByExpirationDateAfter(LocalDate date);
}

