package com.iktpreobuka.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.services.BillService;

@RestController
@RequestMapping("/project/bills")
public class BillController {
	@Autowired
    private BillService billService;

    @GetMapping
    public Iterable<BillEntity> getAllBills() {
        return billService.findAllBills();
    }
 // Dodavanje računa
    @PostMapping("/{offerId}/buyer/{buyerId}")
    public ResponseEntity<BillEntity> addBill(@PathVariable Integer offerId, @PathVariable Integer buyerId, 
                                              @RequestBody BillEntity bill) {
        return ResponseEntity.ok(billService.addBill(offerId, buyerId, bill.getPaymentMade(), bill.getPaymentCanceled(), bill.getBillCreated()));
    }

    // Izmena računa
    @PutMapping("/{id}")
    public ResponseEntity<BillEntity> updateBill(@PathVariable Integer id, @RequestBody BillEntity bill) {
    	return ResponseEntity.ok(billService.updateBill(id, bill.getPaymentMade(), bill.getPaymentCanceled(), bill.getBillCreated()));
    }

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
}
