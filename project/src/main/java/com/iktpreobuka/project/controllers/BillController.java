package com.iktpreobuka.project.controllers;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.services.BillService;
import com.iktpreobuka.project.services.OfferService;
import com.iktpreobuka.project.services.VoucherService;

@RestController
@RequestMapping("/project/bills")
public class BillController {
	@Autowired
    private BillService billService;

	@Autowired
    private OfferService offerService;
	
	@Autowired
	private VoucherService voucherService; 
	
    @GetMapping
    public Iterable<BillEntity> getAllBills() {
        return billService.findAllBills();
    }
 // Dodavanje racuna
    @PostMapping("/{offerId}/buyer/{buyerId}")
    public ResponseEntity<BillEntity> addBill(@PathVariable Integer offerId,
            @PathVariable Integer buyerId,
            @RequestBody BillEntity bill) {
			try {
				BillEntity newBill = billService.addBill(offerId, buyerId, bill.getPaymentMade(), bill.getPaymentCanceled(), bill.getBillCreated());
				return new ResponseEntity<>(newBill, HttpStatus.CREATED);
			} catch (RuntimeException ex) {
				return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
			}
}
//******************** !!ne update available and bought offers
    // Izmena računa
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<BillEntity> updateBill(@PathVariable Integer id, @RequestBody BillEntity bill) {
    	try {
            BillEntity updatedBill = billService.updateBill(id, bill.getPaymentMade(), bill.getPaymentCanceled(), bill.getBillCreated());
            return new ResponseEntity<>(updatedBill, HttpStatus.OK);
        } catch (RuntimeException ex) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }
    
    //ako se svaki put kreira voucer kada je placen racun
//    @PutMapping("/bills/{id}")
//    public ResponseEntity<?> updateBill(@PathVariable Integer id, @RequestBody BillEntity billDetails) {
//        BillEntity bill = billService.findBillById(id);
//        if (bill != null) {
//            bill.setPaymentMade(billDetails.getPaymentMade());
//            // ostale izmene
//            billService.save(bill);
//            if (bill.getPaymentMade()) {
//                VoucherEntity voucher = voucherService.createVoucherFromBill(bill);
//                // Opciono: Možete vratiti vaučer kao deo response-a ili samo potvrditi uspešno kreiranje
//            }
//            return ResponseEntity.ok(bill);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
    // Brisanje računa
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBill(@PathVariable Integer id) {
        billService.deleteBill(id);
        return ResponseEntity.ok().build();
    }

    // Pronalazak svih računa određenog kupca
    @GetMapping("/findByBuyer/{buyerId}")
    public ResponseEntity<List<BillEntity>> findBillsByBuyer(@PathVariable Integer buyerId) {
        return ResponseEntity.ok(billService.findBillsByBuyer(buyerId));
    }
    @GetMapping("/findByPeriod")
    public ResponseEntity<List<BillEntity>> findBillsByPeriod(@RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy")  LocalDate startDate, @RequestParam @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate) {
        List<BillEntity> bills = billService.findBillsInPeriod(startDate.atStartOfDay(), endDate.atStartOfDay());
        return ResponseEntity.ok(bills);
    }
    
    @PostMapping("/{id}/createVoucher")
    public ResponseEntity<?> createVoucherForBill(@PathVariable Integer id) {
        Optional<BillEntity> billOptional = billService.findBillById(id);
        
        if (billOptional.isPresent()) {
            BillEntity bill = billOptional.get();
            if (bill.getPaymentMade()) {
                // Provera da li je račun već plaćen pre kreiranja vaučera
                VoucherEntity voucher = voucherService.createVoucherFromBill(bill);
                return new ResponseEntity<>(voucher, HttpStatus.CREATED); // Vraća kreirani vaučer
            } else {
                // Račun postoji ali nije plaćen
                return new ResponseEntity<>("Bill is not paid yet.", HttpStatus.BAD_REQUEST);
            }
        } else {
            // Račun ne postoji
            return ResponseEntity.notFound().build();
        }
    }
}
