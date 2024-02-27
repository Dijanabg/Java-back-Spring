package com.iktpreobuka.project.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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
    
 // dodavanje vaucera
    @PostMapping("/{offerId}/buyer/{buyerId}")
    public ResponseEntity<VoucherEntity> addVoucher(@PathVariable Integer offerId, @PathVariable Integer buyerId) {
        return new ResponseEntity<>(voucherService.addVoucher(offerId, buyerId), HttpStatus.CREATED);
    }

    // azuriranje vaucera
    @PutMapping("/{id}")
    public ResponseEntity<VoucherEntity> updateVoucher(@PathVariable Integer id, @RequestBody VoucherEntity voucherDetails) {
        return new ResponseEntity<>(voucherService.updateVoucher(id, voucherDetails), HttpStatus.OK);
    }

    // brisanje vaucera
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVoucher(@PathVariable Integer id) {
        voucherService.deleteVoucher(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // pronalazak vaucera odredjenog kupca
    @GetMapping("/findByBuyer/{buyerId}")
    public ResponseEntity<List<VoucherEntity>> findVouchersByBuyer(@PathVariable Integer buyerId) {
        return new ResponseEntity<>(voucherService.findVouchersByBuyer(buyerId), HttpStatus.OK);
    }

    // pronalazak vaucera odredjene ponude
    @GetMapping("/findByOffer/{offerId}")
    public ResponseEntity<List<VoucherEntity>> findVouchersByOffer(@PathVariable Integer offerId) {
        return new ResponseEntity<>(voucherService.findVouchersByOffer(offerId), HttpStatus.OK);
    }

    // pronalazak neisteklih vaucera
    @GetMapping("/findNonExpiredVouchers")
    public ResponseEntity<List<VoucherEntity>> findNonExpiredVouchers() {
        return new ResponseEntity<>(voucherService.findNonExpiredVouchers(), HttpStatus.OK);
    }
    @PostMapping("/addWithEmail")
    public ResponseEntity<VoucherEntity> addVoucherWithEmail(@RequestParam Integer offerId, @RequestParam Integer userId) {
        try {
            VoucherEntity createdVoucher = voucherService.addVoucherWithEmail(offerId, userId);
            return new ResponseEntity<>(createdVoucher, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/sendCustomVoucherEmail")
    public ResponseEntity<String> sendCustomEmail(@RequestParam String to, @RequestParam String subject, @RequestBody String content, @RequestParam(defaultValue = "false") boolean isHtml) {
        try {
            voucherService.sendVoucherEmail(to, subject, content, isHtml);
            return new ResponseEntity<>("Email successfully sent!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error sending email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping("/sendVoucherEmail")
    @Transactional
    //something smell very bad :)
    public ResponseEntity<String> sendVoucherEmail(@RequestParam Integer voucherId, @RequestParam String to, @RequestParam String subject) {
        try {
            Optional<VoucherEntity> voucherOptional = voucherService.findVoucherById(voucherId);
            
            if (!voucherOptional.isPresent()) {
                return new ResponseEntity<>("Voucher not found.", HttpStatus.NOT_FOUND);
            }
            
            VoucherEntity voucher = voucherOptional.get();
            
            // metoda za generisanje sadržaja e-maila
            String content = voucherService.buildVoucherEmailContent(voucher);
            
            voucherService.sendVoucherEmail(to, subject, content, false);
            
            return new ResponseEntity<>("Email successfully sent!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error sending email: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

