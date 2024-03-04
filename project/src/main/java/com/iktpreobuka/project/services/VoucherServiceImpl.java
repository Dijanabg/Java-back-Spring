package com.iktpreobuka.project.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.OfferEntity;
import com.iktpreobuka.project.entities.UserEntity;
import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.entities.dto.VoucherDTO;
import com.iktpreobuka.project.repositories.OfferRepository;
import com.iktpreobuka.project.repositories.UserRepository;
import com.iktpreobuka.project.repositories.VoucherRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class VoucherServiceImpl implements VoucherService{
	@Autowired
    private VoucherRepository voucherRepository;

	@Autowired
    private UserRepository userRepository;

    @Autowired
    private OfferRepository offerRepository;
    
    @Autowired
    private EmailService emailService;
    
    @Autowired
	public JavaMailSender emailSender;
    
    @Override
    public Iterable<VoucherEntity> findAllVouchers() {
        return voucherRepository.findAll();
    }
    
    @Override
	public Optional<VoucherEntity> findVoucherById(Integer id) {
	    return voucherRepository.findById(id);
	}
    //dodavanje vaucera
	@Override
	public VoucherEntity addVoucher(Integer offerId, Integer userId) {
		UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    OfferEntity offer = offerRepository.findById(offerId)
	            .orElseThrow(() -> new RuntimeException("Offer not found"));
	    VoucherDTO voucherDTO = new VoucherDTO();
	    voucherDTO.setExpirationDate(LocalDate.now().plusDays(30)); // postavljanje datuma isteka na 30 dana od trenutka kreiranja
	    voucherDTO.setIsUsed(false); // Inicijalno 
	    
	    VoucherEntity voucher = new VoucherEntity();
	        voucher.setUser(user);
	        voucher.setOffer(offer);
	        voucher.setExpirationDate(voucherDTO.getExpirationDate());
	        voucher.setIsUsed(voucherDTO.getIsUsed());

	    return voucherRepository.save(voucher);
	}
	//ukoliko dodajemo vaucet i saljemo mail
	@Override
	public VoucherEntity addVoucherWithEmail(Integer offerId, Integer userId) {
	    UserEntity user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));
	    OfferEntity offer = offerRepository.findById(offerId)
	            .orElseThrow(() -> new RuntimeException("Offer not found"));

	    VoucherEntity voucher = new VoucherEntity();
	    voucher.setUser(user);
	    voucher.setOffer(offer);
	    voucher.setExpirationDate(LocalDate.now().plusDays(30)); // Primer važenja 30 dana
	    voucher.setIsUsed(false);

	    VoucherEntity savedVoucher = voucherRepository.save(voucher);
	
	    // slanje e-maila sa detaljima o vauceru
	    String emailContent = "<h1>Detalji Vašeg vaučera</h1>" +
	            "<table border='1'>" +
	            "<tr>" +
	            "<th>Kupac</th><th>Ponuda</th><th>Cena</th><th>Datum isteka</th>" +
	            "</tr>" +
	            "<tr>" +
	            String.format("<td>%s</td><td>%s</td><td>%s</td><td>%s</td>", 
	                          voucher.getUser().getFirstName(), voucher.getOffer().getOfferName(), 
	                          voucher.getOffer().getActionPrice(), voucher.getExpirationDate().toString()) +
	            "</tr>" +
	            "</table>";

	    emailService.sendVoucherEmail(user.getEmail(), "Detalji Vašeg vaučera", emailContent);

	    return savedVoucher;
	}
	
	//salje vaucer bez dodavanja novog
	@Override
	public void sendVoucherEmail(String to, String subject, String content, boolean isHtml) {
	    //SimpleMailMessage message = new SimpleMailMessage();
	    MimeMessage mimeMessage = emailSender.createMimeMessage();

	    try {
	        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, false, "utf-8");
	        mimeMessage.setContent(content, isHtml ? "text/html" : "text/plain");
	        helper.setTo(to);
	        helper.setSubject(subject);
	        helper.setText(content, isHtml);
	    } catch (MessagingException e) {
	        throw new RuntimeException(e);
	    }

	    emailSender.send(mimeMessage);
	}
	//azuriranje vaucera
	@Override
	public VoucherEntity updateVoucher(Integer id, VoucherEntity voucherDetails) {
		VoucherEntity voucher = voucherRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Voucher not found"));
	        
	        voucher.setIsUsed(voucherDetails.getIsUsed());
	        voucher.setExpirationDate(voucherDetails.getExpirationDate());
	        // dodati ostale 

	    return voucherRepository.save(voucher);
	}

	//brisanje vaucera
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
	    LocalDate today = LocalDate.now();
	    return voucherRepository.findByExpirationDateAfterOrExpirationDate(today, today);
	}
	
	//kreiranje vaucera placenog racuna
	@Override
	@Transactional
	public VoucherEntity createVoucherFromBill(BillEntity bill) {
	    VoucherEntity voucher = new VoucherEntity();
	    voucher.setExpirationDate(LocalDate.now().plusMonths(6)); //postavka roka važenja na 6 meseci
	    voucher.setIsUsed(false);
	    voucher.setOffer(bill.getOffer()); 
	    voucher.setUser(bill.getUser()); 
	    return voucherRepository.save(voucher);
	}
	
	@Override
	public String buildVoucherEmailContent(VoucherEntity voucher) {
	    String content = "Poštovani,\n\n"
	                   + "Hvala Vam što koristite naše usluge. Ovo je Vaš vaučer za popust:\n\n"
	                   + "Ponuda: " + voucher.getOffer() + "\n"
	                   + "Datum isteka: " + voucher.getExpirationDate().toString() + "\n"
	                   + "Kod vaučera: " + voucher.getId().toString() + "\n\n"
	                   + "Da biste iskoristili vaučer, jednostavno pokažite ovaj e-mail prilikom plaćanja ili unesite kod vaučera na našoj veb stranici.\n\n"
	                   + "Srdačno,\n"
	                   + "Vaš tim";
	    return content;
	}
}
