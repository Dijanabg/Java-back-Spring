package com.iktpreobuka.project.entities;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.security.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name="bill")
public class BillEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
    private Integer id;
	@Column(name = "payment_made", nullable = false)
	@JsonView(Views.Private.class)
    private Boolean paymentMade;
	@Column(name = "payment_canceled", nullable = false)
	@JsonView(Views.Private.class)
    private Boolean paymentCanceled;
    
    @JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "bill_created", nullable = false)
    @JsonView(Views.Public.class)
    private LocalDateTime billCreated;
	public BillEntity() {
		super();
	}
	public BillEntity(Integer id, Boolean paymentMade, Boolean paymentCanceled, LocalDateTime billCreated) {
		super();
		this.id = id;
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
	}
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offer_id", nullable = false)
	@JsonView(Views.Private.class)
    private OfferEntity offer;
	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
	@JsonView(Views.Private.class)
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
	public LocalDateTime getBillCreated() {
		return billCreated;
	}
	public void setBillCreated(LocalDateTime billCreated) {
		this.billCreated = billCreated;
	}
	@Override
	public String toString() {
		return "BillEntity [id=" + id + ", paymentMade=" + paymentMade + ", paymentCanceled=" + paymentCanceled
				+ ", billCreated=" + billCreated + "]";
	}

}
