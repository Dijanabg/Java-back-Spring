package com.iktpreobuka.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.dto.BillDTO;
import com.iktpreobuka.project.entities.dto.ReportItem;

public interface BillService{
	Optional<BillEntity> findBillById(Integer id);
	
	Iterable<BillEntity> findAllBills();
	
	BillEntity addBill(Integer offerId, Integer buyerId, BillDTO billDTO);

    BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCancelled, LocalDateTime billCreated);

    void deleteBill(Integer id);

    List<BillEntity> findBillsByBuyer(Integer buyerId);

    List<BillEntity> findBillsByCategory(Integer categoryId);

    List<BillEntity> findBillsByDate(LocalDateTime startDate, LocalDateTime endDate);
    
    List<BillEntity> findBillsInPeriod(LocalDateTime localDateTime, LocalDateTime localDateTime2);
    
    boolean existsActiveBillsForCategory(Integer categoryId);
    
    void cancelBillsForExpiredOffer(Integer offerId);
    
    List<ReportItem> generateReportByDate(LocalDateTime startDate, LocalDateTime endDate);
    
    List<ReportItem> generateReportByCategory(LocalDateTime startDate, LocalDateTime endDate, Integer categoryId);
    
}
