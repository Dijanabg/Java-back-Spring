package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.repositories.BillRepository;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;

@Service
public class BillServiceImpl implements BillService {
	@Autowired
	private BillRepository billRepository;
	@Autowired
    private OfferRepository offerRepository;
    @Autowired
    private UserRepository userRepository;
    
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
    public Iterable<BillEntity> findAllBills() {
        return billRepository.findAll();
    }
    
    @Override
    public BillEntity addBill(Integer offerId, Integer buyerId, boolean paymentMade, boolean paymentCancelled, LocalDate billCreated) {
        Optional<OfferEntity> offer = offerRepository.findById(offerId);
        Optional<UserEntity> buyer = userRepository.findById(buyerId);
        if (offer.isPresent() && buyer.isPresent()) {
            BillEntity bill = new BillEntity();
            bill.setOffer(offer.get());
            bill.setUser(buyer.get()); // Ako je veza između korisnika i računa postavljena u entitetu BillEntity
            bill.setPaymentMade(paymentMade);
            bill.setPaymentCanceled(paymentCancelled);
            bill.setBillCreated(billCreated);
            return billRepository.save(bill);
        }
        // Handle case where offer or buyer does not exist
        return null;
    }

    @Override
    public BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCanceled) {
        Optional<BillEntity> bill = billRepository.findById(id);
        if (bill.isPresent()) {
            bill.get().setPaymentMade(paymentMade);
            bill.get().setPaymentCanceled(paymentCanceled);
            return billRepository.save(bill.get());
        }
        // Handle case where bill does not exist
        return null;
    }

    @Override
    public void deleteBill(Integer id) {
        billRepository.deleteById(id);
    }

    @Override
    public List<BillEntity> findBillsByBuyer(Integer buyerId) {
    	return billRepository.findByUser_Id(buyerId);
    }

    @Override
    public List<BillEntity> findBillsByCategory(Integer categoryId) {
    	return billRepository.findBillsByCategory(categoryId);
    }

    @Override
    public List<BillEntity> findBillsByDate(LocalDate startDate, LocalDate endDate) {
    	 return billRepository.findByBillCreatedBetween(startDate, endDate);
    }
}
