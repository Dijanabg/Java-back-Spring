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
        Optional<OfferEntity> offerf = offerRepository.findById(offerId);
        Optional<UserEntity> buyer = userRepository.findById(buyerId);
        if (offerf.isPresent() && buyer.isPresent()) {
        	OfferEntity offer = offerf.get();
            
            // da li postoje ponude pre nego što napravi racun
            if(offer.getAvailableOffers() > 0) {
                // Ažuriraj broj dostupnih i kupljenih ponuda
                offer.setAvailableOffers(offer.getAvailableOffers() - 1);
                offer.setBoughtOffers(offer.getBoughtOffers() + 1);
                offerRepository.save(offer); // Sačuvaj ažuriranu ponudu
	            BillEntity bill = new BillEntity();
	            bill.setOffer(offerf.get());
	            bill.setUser(buyer.get()); // Ako je veza između korisnika i računa postavljena u entitetu BillEntity
	            bill.setPaymentMade(paymentMade);
	            bill.setPaymentCanceled(paymentCancelled);
	            bill.setBillCreated(billCreated);
	            return billRepository.save(bill);
	        }
	        else {
	            throw new RuntimeException("No available offers left for this offer");
	        }
        } else {
            throw new RuntimeException("Offer or buyer not found.");
        }
    }

    @Override
    public BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDate billCreated) {
    	//me znam kako s optional
    	BillEntity bill = billRepository.findById(id).orElseThrow(() -> new RuntimeException("Bill not found."));
        	// Provjeri da li je došlo do promene statusa računa na otkazano
    	if (!bill.getPaymentCanceled() && paymentCanceled) {
                // Ako je račun prethodno bio aktivan, a sada se otkazuje
                
                OfferEntity offer = bill.getOffer();
                // Povećaj broj dostupnih i smanji broj kupljenih ponuda
                offer.setAvailableOffers(offer.getAvailableOffers() + 1);
                offer.setBoughtOffers(offer.getBoughtOffers() - 1);
                offerRepository.save(offer); // Sačuvaj ažuriranu ponudu
            }
    	bill.setPaymentMade(paymentMade);
        bill.setPaymentCanceled(paymentCanceled);
        bill.setBillCreated(billCreated);
           
        return billRepository.save(bill);

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
