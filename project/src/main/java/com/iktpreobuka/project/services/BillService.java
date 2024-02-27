package com.iktpreobuka.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillService{
	Optional<BillEntity> findBillById(Integer id);
	
	Iterable<BillEntity> findAllBills();
	
	BillEntity addBill(Integer offerId, Integer buyerId, boolean paymentMade, boolean paymentCancelled, LocalDateTime billCreated);

    BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCancelled, LocalDateTime billCreated);

    void deleteBill(Integer id);

    List<BillEntity> findBillsByBuyer(Integer buyerId);

    List<BillEntity> findBillsByCategory(Integer categoryId);

    List<BillEntity> findBillsByDate(LocalDateTime startDate, LocalDateTime endDate);
    
    List<BillEntity> findBillsInPeriod(LocalDateTime localDateTime, LocalDateTime localDateTime2);
    
    boolean existsActiveBillsForCategory(Integer categoryId);
    
    void cancelBillsForExpiredOffer(Integer offerId);
    
}
