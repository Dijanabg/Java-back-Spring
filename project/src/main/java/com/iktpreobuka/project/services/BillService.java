package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;

import com.iktpreobuka.project.entities.BillEntity;

public interface BillService{
	Iterable<BillEntity> findAllBills();
	
	BillEntity addBill(Integer offerId, Integer buyerId, boolean paymentMade, boolean paymentCancelled, LocalDate billCreated);

    BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCancelled);

    void deleteBill(Integer id);

    List<BillEntity> findBillsByBuyer(Integer buyerId);

    List<BillEntity> findBillsByCategory(Integer categoryId);

    List<BillEntity> findBillsByDate(LocalDate startDate, LocalDate endDate);
}
