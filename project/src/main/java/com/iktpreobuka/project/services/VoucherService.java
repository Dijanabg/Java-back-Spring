package com.iktpreobuka.project.services;

import java.util.List;

import com.iktpreobuka.project.entities.VoucherEntity;

public interface VoucherService {
	 Iterable<VoucherEntity> findAllVouchers();
	 VoucherEntity addVoucher(Integer offerId, Integer userId);
	 VoucherEntity updateVoucher(Integer id, VoucherEntity voucherDetails);
	 void deleteVoucher(Integer id);
	 List<VoucherEntity> findVouchersByBuyer(Integer buyerId);
	 List<VoucherEntity> findVouchersByOffer(Integer offerId);
	 List<VoucherEntity> findNonExpiredVouchers();
}
