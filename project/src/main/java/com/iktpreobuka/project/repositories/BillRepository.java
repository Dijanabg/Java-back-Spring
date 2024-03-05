package com.iktpreobuka.project.repositories;


import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.dto.ReportItem;

public interface BillRepository extends CrudRepository<BillEntity, Integer>{
	List<BillEntity> findByUser_Id(Integer userId);

    @Query("SELECT b FROM BillEntity b WHERE b.offer.category.id = :categoryId")
    List<BillEntity> findBillsByCategory(@Param("categoryId") Integer categoryId);

    List<BillEntity> findByBillCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    List<BillEntity> findAllByBillCreatedBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    long countByOfferCategoryIdAndBillCreatedBefore(Integer categoryId, LocalDateTime beforeDate);
    
    List<BillEntity> findAllByOfferId(Integer offerId);
    
    @Query("SELECT new com.iktpreobuka.project.entities.dto.ReportItem(b.billCreated as date, SUM(b.amount) as income, COUNT(b) as numberOfOffers) FROM BillEntity b WHERE b.billCreated BETWEEN :startDate AND :endDate GROUP BY b.billCreated")
    List<ReportItem> findSalesByDate(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Primer za dohvatanje podataka o prodaji po kategoriji u datom periodu
    @Query("SELECT new com.iktpreobuka.project.entities.dto.ReportItem(b.billCreated as date, SUM(b.amount) as income, COUNT(b) as numberOfOffers) FROM BillEntity b WHERE b.offer.category.id = :categoryId AND b.billCreated BETWEEN :startDate AND :endDate GROUP BY b.billCreated")
    List<ReportItem> findSalesByCategoryAndDate(@Param("categoryId") Integer categoryId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
}
