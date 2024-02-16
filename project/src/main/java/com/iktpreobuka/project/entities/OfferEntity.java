package com.iktpreobuka.project.entities;

import java.util.Date;

public class OfferEntity {
	private Integer id;
	private String offerName;
	private String offerDescription;
	private Date offerCreated;
	private Date offerExpires;
	private Double regularPrice;
	private Double actionPrice;
	private String imagePath;
	private Integer availableOffers;
	private Integer boughtOffers;
	private EOfferStatus offerStatus;
	public OfferEntity() {
		super();
	}
	public OfferEntity(Integer id, String offerName, String offerDescription, Date offerCreated,
			Date offerExpires, Double regularPrice, Double actionPrice, String imagePath, Integer availableOffers,
			Integer boughtOffers, EOfferStatus offerStatus) {
		super();
		this.id = id;
		this.offerName = offerName;
		this.offerDescription = offerDescription;
		this.offerCreated = offerCreated;
		this.offerExpires = offerExpires;
		this.regularPrice = regularPrice;
		this.actionPrice = actionPrice;
		this.imagePath = imagePath;
		this.availableOffers = availableOffers;
		this.boughtOffers = boughtOffers;
		this.offerStatus = offerStatus;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getOfferName() {
		return offerName;
	}
	public void setOfferName(String offerName) {
		this.offerName = offerName;
	}
	public String getOfferDescription() {
		return offerDescription;
	}
	public void setOfferDescription(String offerDescription) {
		this.offerDescription = offerDescription;
	}
	public Date getOfferCreated() {
		return offerCreated;
	}
	public void setOfferCreated(Date offerCreated) {
		this.offerCreated = offerCreated;
	}
	public Date getOfferExpires() {
		return offerExpires;
	}
	public void setOfferExpires(Date offerExpires) {
		this.offerExpires = offerExpires;
	}
	public Double getRegularPrice() {
		return regularPrice;
	}
	public void setRegularPrice(Double regularPrice) {
		this.regularPrice = regularPrice;
	}
	public Double getActionPrice() {
		return actionPrice;
	}
	public void setActionPrice(Double actionPrice) {
		this.actionPrice = actionPrice;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public Integer getAvailableOffers() {
		return availableOffers;
	}
	public void setAvailableOffers(Integer availableOffers) {
		this.availableOffers = availableOffers;
	}
	public Integer getBoughtOffers() {
		return boughtOffers;
	}
	public void setBoughtOffers(Integer boughtOffers) {
		this.boughtOffers = boughtOffers;
	}
	public EOfferStatus getOfferStatus() {
		return offerStatus;
	}
	public void setOfferStatus(EOfferStatus offerStatus) {
		this.offerStatus = offerStatus;
	}
	@Override
	public String toString() {
		return "OfferEntity [id=" + id + ", offerName=" + offerName + ", offerDescription=" + offerDescription
				+ ", offerCreated=" + offerCreated + ", offerExpires=" + offerExpires + ", regularPrice=" + regularPrice
				+ ", actionPrice=" + actionPrice + ", imagePath=" + imagePath + ", availableOffers=" + availableOffers
				+ ", boughtOffers=" + boughtOffers + ", offerStatus=" + offerStatus + "]";
	}
	
	
}
