package com.iktpreobuka.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.services.VoucherService;

@RestController
@RequestMapping("/project/vouchers")
public class VoucherController {

    @Autowired
    private VoucherService voucherService;

    @GetMapping
    public Iterable<VoucherEntity> getAllVouchers() {
        return voucherService.findAllVouchers();
    }
    
 // Dodavanje vaučera
    @PostMapping("/{offerId}/buyer/{buyerId}")
    public ResponseEntity<VoucherEntity> addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
        return new ResponseEntity<>(voucherService.addVoucher(offerId, buyerId), HttpStatus.CREATED);
    }

    // Ažuriranje vaučera
    @PutMapping("/{id}")
    public ResponseEntity<VoucherEntity> updateVoucher(@PathVariable Integer id, @RequestBody VoucherEntity voucherDetails) {
        return new ResponseEntity<>(voucherService.updateVoucher(id, voucherDetails), HttpStatus.OK);
    }

    // Brisanje vaučera
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Integer id) {
        voucherService.deleteVoucher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Pronalazak vaučera određenog kupca
    @GetMapping("/findByBuyer/{buyerId}")
    public ResponseEntity<List<VoucherEntity>> findVouchersByBuyer(@PathVariable Integer buyerId) {
        return new ResponseEntity<>(voucherService.findVouchersByBuyer(buyerId), HttpStatus.OK);
    }

    // Pronalazak vaučera određene ponude
    @GetMapping("/findByOffer/{offerId}")
    public ResponseEntity<List<VoucherEntity>> findVouchersByOffer(@PathVariable Integer offerId) {
        return new ResponseEntity<>(voucherService.findVouchersByOffer(offerId), HttpStatus.OK);
    }

    // Pronalazak neisteklih vaučera
    @GetMapping("/findNonExpiredVouchers")
    public ResponseEntity<List<VoucherEntity>> findNonExpiredVouchers() {
        return new ResponseEntity<>(voucherService.findNonExpiredVouchers(), HttpStatus.OK);
    }
}

