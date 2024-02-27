package com.iktpreobuka.project.services;

import java.util.List;
import java.util.Optional;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.VoucherEntity;

public interface VoucherService {
	 Iterable<VoucherEntity> findAllVouchers();
	 VoucherEntity addVoucher(Integer offerId, Integer userId);
	 VoucherEntity updateVoucher(Integer id, VoucherEntity voucherDetails);
	 void deleteVoucher(Integer id);
	 List<VoucherEntity> findVouchersByBuyer(Integer buyerId);
	 List<VoucherEntity> findVouchersByOffer(Integer offerId);
	 List<VoucherEntity> findNonExpiredVouchers();
	 VoucherEntity createVoucherFromBill(BillEntity bill);
	 VoucherEntity addVoucherWithEmail(Integer offerId, Integer userId);
	 void sendVoucherEmail(String to, String subject, String content, boolean isHtml);
	Optional<VoucherEntity> findVoucherById(Integer id);
	String buildVoucherEmailContent(VoucherEntity voucher);
}
