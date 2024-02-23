package com.iktpreobuka.project.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillRepository extends CrudRepository<BillEntity, Integer>{
	List<BillEntity> findByUser_Id(Integer userId);

    @Query("SELECT b FROM BillEntity b WHERE b.offer.category.id = :categoryId")
    List<BillEntity> findBillsByCategory(@Param("categoryId") Integer categoryId);

    List<BillEntity> findByBillCreatedBetween(LocalDate startDate, LocalDate endDate);
}
