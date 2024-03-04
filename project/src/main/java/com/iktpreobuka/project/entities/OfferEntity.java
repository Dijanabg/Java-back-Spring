package com.iktpreobuka.project.entities;

import java.time.LocalDateTime;
import java.util.List;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.security.Views;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "offers")
public class OfferEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonView(Views.Public.class)
	private Integer id;
	
	@Column(name = "offer_name", nullable = false)
	@JsonView(Views.Public.class)
	@NotBlank(message = "Name must be provided.")
	private String offerName;
	@JsonView(Views.Public.class)
	@NotBlank(message = "Description must be provided.")
	@Size(min=5, max=200, message = "Description name must be between {min} and {max} characters long.")
	@Column(name = "offer_description", nullable = false)
	
	private String offerDescription;
	
	@JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "offer_created", nullable = false)
	@JsonView(Views.Public.class)
	private LocalDateTime offerCreated;
	
	@JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy HH:mm:ss")
	@Column(name = "offer_expires", nullable = false)
	@JsonView(Views.Public.class)
	private LocalDateTime offerExpires;
	
	@Column(name = "regular_price", nullable = false)
	@JsonView(Views.Public.class)
	@Min(value=1, message = "Action price must be greater than 1.")
	private Double regularPrice;
	
	@Column(name = "action_price", nullable = false)
	@JsonView(Views.Public.class)
	@Min(value=1, message = "Action price must be greater than 1.")
	private Double actionPrice;
	
	@Column(name = "image_path", nullable = true)
	@JsonView(Views.Public.class)
	private String imagePath;
	
	@Column(name = "available_offers", nullable = false)
	@JsonView(Views.Public.class)
	@Min(value=1, message = "Number available must be greater than 0.")
	private Integer availableOffers;
	
	@Column(name = "bought_offers", nullable = false)
	@JsonView(Views.Public.class)
	@Min(value=1, message = "Number available must be greater than 0.")
	private Integer boughtOffers;
	
	@Enumerated(EnumType.STRING)
    @Column(name = "offer_status", nullable = false)
	@JsonView(Views.Public.class)
	private EOfferStatus offerStatus;
	
	@ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
	@JsonView(Views.Public.class)
    private CategoryEntity category;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonView(Views.Public.class)
    private UserEntity user;
    
    @JsonIgnore
    @OneToMany(mappedBy = "offer", fetch = FetchType.LAZY)
    private List<BillEntity> bills;
    
//    @OneToMany(mappedBy = "offer")
//    private List<VoucherEntity> vouchers;
    
	public List<BillEntity> getBills() {
		return bills;
	}
	public void setBills(List<BillEntity> bills) {
		this.bills = bills;
	}
	public OfferEntity() {
		super();
	}
	public OfferEntity(Integer id, String offerName, String offerDescription,LocalDateTime offerCreated,
			LocalDateTime offerExpires, Double regularPrice, Double actionPrice, String imagePath, Integer availableOffers,
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
	public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
    public CategoryEntity getCategory() {
        return category;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
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
	public LocalDateTime getOfferCreated() {
		return offerCreated;
	}
	public void setOfferCreated(LocalDateTime offerCreated) {
		this.offerCreated = offerCreated;
	}
	public LocalDateTime getOfferExpires() {
		return offerExpires;
	}
	public void setOfferExpires(LocalDateTime localDate) {
		this.offerExpires = localDate;
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
