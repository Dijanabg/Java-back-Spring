package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
    private VoucherRepository voucherRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;
    
    @Override
    public Iterable<VoucherEntity> findAllVouchers() {
        return voucherRepository.findAll();
    }

	@Override
	public VoucherEntity addVoucher(Integer offerId, Integer userId) {
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    OfferEntity offer = offerRepository.findById(offerId)
	            .orElseThrow(() -> new RuntimeException("Offer not found"));

	    VoucherEntity voucher = new VoucherEntity();
	        voucher.setUser(user);
	        voucher.setOffer(offer);
	        voucher.setExpirationDate(LocalDate.now().plusDays(30)); // primer vazenje 30 dana
	        voucher.setIsUsed(false);

	    return voucherRepository.save(voucher);
	}

	@Override
	public VoucherEntity updateVoucher(Integer id, VoucherEntity voucherDetails) {
		VoucherEntity voucher = voucherRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Voucher not found"));
	        
	        voucher.setIsUsed(voucherDetails.getIsUsed());
	        voucher.setExpirationDate(voucherDetails.getExpirationDate());
	        // dodati ostale 

	    return voucherRepository.save(voucher);
	}

	@Override
	public void deleteVoucher(Integer id) {
		VoucherEntity voucher = voucherRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Voucher not found"));
	        voucherRepository.delete(voucher);
		
	}

	@Override
	public List<VoucherEntity> findVouchersByBuyer(Integer buyerId) {
		return voucherRepository.findByUserId(buyerId);
	}

	@Override
	public List<VoucherEntity> findVouchersByOffer(Integer offerId) {
		return voucherRepository.findByOfferId(offerId);
	}

	@Override
	public List<VoucherEntity> findNonExpiredVouchers() {
		return voucherRepository.findByExpirationDateAfter(LocalDate.now());
	}
}
