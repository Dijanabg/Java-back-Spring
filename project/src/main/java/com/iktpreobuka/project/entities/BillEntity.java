package com.iktpreobuka.project.entities;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BillEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Boolean paymentMade;
    private Boolean paymentCanceled;
    private LocalDate billCreated;
	public BillEntity() {
		super();
	}
	public BillEntity(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDate billCreated) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
    private OfferEntity offer;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
	
	
	public UserEntity getUser() {
		return user;
	}
	public void setUser(UserEntity user) {
		this.user = user;
	}
	public OfferEntity getOffer() {
		return offer;
	}
	public void setOffer(OfferEntity offer) {
		this.offer = offer;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Boolean getPaymentMade() {
		return paymentMade;
	}
	public void setPaymentMade(Boolean paymentMade) {
		this.paymentMade = paymentMade;
	}
	public Boolean getPaymentCanceled() {
		return paymentCanceled;
	}
	public void setPaymentCanceled(Boolean paymentCanceled) {
		this.paymentCanceled = paymentCanceled;
	}
	public LocalDate getBillCreated() {
		return billCreated;
	}
	public void setBillCreated(LocalDate billCreated) {
		this.billCreated = billCreated;
	}
	@Override
	public String toString() {
		return "BillEntity [id=" + id + ", paymentMade=" + paymentMade + ", paymentCanceled=" + paymentCanceled
				+ ", billCreated=" + billCreated + "]";
	}

}
