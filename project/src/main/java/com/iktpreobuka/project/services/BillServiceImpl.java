package com.iktpreobuka.project.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.dto.BillDTO;
import com.iktpreobuka.project.entities.dto.ReportItem;
import com.iktpreobuka.project.mappers.BillMapper;
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
    @Autowired
    private BillMapper billMapper;
    
    public BillServiceImpl(BillRepository billRepository) {
        this.billRepository = billRepository;
    }

    @Override
	public Optional<BillEntity> findBillById(Integer id) {
	    return billRepository.findById(id);
	}
    @Override
    public Iterable<BillEntity> findAllBills() {
        return billRepository.findAll();
    }
    
    //dodavanje racuna
    @Override
    public BillEntity addBill(Integer offerId, Integer buyerId, BillDTO billDTO) {
        Optional<OfferEntity> offerf = offerRepository.findById(offerId);
        Optional<UserEntity> buyer = userRepository.findById(buyerId);
        if (offerf.isPresent() && buyer.isPresent()) {
        	OfferEntity offer = offerf.get();
            
            // da li postoje ponude pre nego sto napravi racun
            if(offer.getAvailableOffers() > 0) {
                // azuriranje broja dostupnih i kupljenih ponuda
                offer.setAvailableOffers(offer.getAvailableOffers() - 1);
                offer.setBoughtOffers(offer.getBoughtOffers() + 1);
                offerRepository.save(offer); // sacuvaj azuriranu ponudu
                
	            BillEntity bill = billMapper.toEntity(billDTO);
	            bill.setOffer(offerf.get());
	            bill.setUser(buyer.get());
//	            bill.setPaymentMade(billDTO.getPaymentMade());
//	            bill.setPaymentCanceled(billDTO.getPaymentCanceled());
//	            bill.setBillCreated(billDTO.getBillCreated());
	            //BillEntity savedBill = billRepository.save(bill);
	            
	            //return billMaper.toDto(savedBill);
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
    public BillEntity updateBill(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDateTime billCreated) {
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
    public List<BillEntity> findBillsByDate(LocalDateTime startDate, LocalDateTime endDate) {
    	 return billRepository.findByBillCreatedBetween(startDate, endDate);
    }
    @Override
    public List<BillEntity> findBillsInPeriod(LocalDateTime startDate, LocalDateTime endDate) {
        return billRepository.findAllByBillCreatedBetween(startDate, endDate);
    }
    
    @Override
    public boolean existsActiveBillsForCategory(Integer categoryId) {
        // Pretpostavlja se da BillRepository ima metodu koja vraća broj aktivnih računa za ponude iz kategorije
        return billRepository.countByOfferCategoryIdAndBillCreatedBefore(categoryId, LocalDateTime.now()) > 0;
    }
    
    @Override
    public void cancelBillsForExpiredOffer(Integer offerId) {
        List<BillEntity> bills = billRepository.findAllByOfferId(offerId);
        for (BillEntity bill : bills) {
            bill.setPaymentCanceled(true);
            billRepository.save(bill);
        }
    }
    @Override
    public List<ReportItem> generateReportByDate(LocalDateTime startDate, LocalDateTime endDate) {
        // Implementacija će zavisiti od strukture vaše baze i modela
        // Ovo je samo ilustracija kako bi metoda mogla izgledati
        List<ReportItem> reportItems = billRepository.findSalesByDate(startDate, endDate);
        return reportItems;
    }

    @Override
    public List<ReportItem> generateReportByCategory(LocalDateTime startDate, LocalDateTime endDate, Integer categoryId) {
        
        List<ReportItem> reportItems = billRepository.findSalesByCategoryAndDate(categoryId, endDate, startDate);
        return reportItems;
    }
}
