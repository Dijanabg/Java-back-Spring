package com.iktpreobuka.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.annotation.JsonView;
import com.iktpreobuka.project.security.Views;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@JsonView(Views.Admin.class)
public class UserEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		@JsonView(Views.Public.class)
	    private Integer id;
		
		@Column(name = "first_name", nullable = false)
		@JsonView(Views.Private.class)
		@NotBlank(message = "First name must be provided.")
		@Size(min=2, max=30, message = "First name must be between {min} and {max} characters long.")
	    private String firstName;
		
		@Column(name = "last_name", nullable = false)
		@JsonView(Views.Private.class)
		@NotBlank(message = "Last name must be provided.")
		@Size(min=2, max=30, message = "First name must be between {min} and {max} characters long.")
	    private String lastName;
		
		@Column(name = "user_name", nullable = true)
		@JsonView(Views.Public.class)
		@NotBlank(message = "Username must be provided.")
		@Size(min=5, max=20, message = "Username must be between {min} and {max} characters long.")
	    private String username;
		
		
		@Column(name = "password", nullable = false)
		@JsonView(Views.Private.class)
		@JsonProperty(access = Access.WRITE_ONLY)
		@JsonIgnore
		@NotBlank(message = "Password must be provided.")
		@Size(min=5, message = "Password must be between {min} and {max} characters long.")
		@Pattern(regexp = "([0-9].*[a-zA-Z])|([a-zA-Z].*[0-9])", message = "Password must contain letters and digits.")
	    private String password;
		
	    @Column(name = "email", nullable = false, unique = true)
	    @JsonView(Views.Private.class)
		@NotNull(message = "Email must be provided.")
		@Email(message = "Email is not valid.")
		protected String email;
	    
	    @Enumerated(EnumType.STRING)
	    @Column(name = "user_role", nullable = false)
	    @JsonView(Views.Admin.class)
	    private EUserRole userRole;
	    
	    @JsonIgnore
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	    private List<OfferEntity> offers = new ArrayList<>();
	    
//	    @OneToMany(mappedBy = "user")
//	    private List<VoucherEntity> vouchers;
	    
		public UserEntity() {
			super();
		}
		
		
		public UserEntity(Integer id, String firstName, String lastName, String username, String password, String email,
				EUserRole userRole) {
			super();
			this.id = id;
			this.firstName = firstName;
			this.lastName = lastName;
			this.username = username;
			this.password = password;
			this.email = email;
			this.userRole = userRole;
		}
		@JsonIgnore
		@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	    private List<BillEntity> bills;
		
		public List<OfferEntity> getOffers() {
			return offers;
		}


		public void setOffers(List<OfferEntity> offers) {
			this.offers = offers;
		}


		public List<BillEntity> getBills() {
			return bills;
		}


		public void setBills(List<BillEntity> bills) {
			this.bills = bills;
		}


		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getFirstName() {
			return firstName;
		}
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		public String getLastName() {
			return lastName;
		}
		public void setLastName(String lastName) {
			this.lastName = lastName;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		
		public EUserRole getUserRole() {
			return userRole;
		}


		public void setUserRole(EUserRole userRole) {
			this.userRole = userRole;
		}


		@Override
		public String toString() {
			return "UserEntity [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", username="
					+ username + ", password=" + password + ", email=" + email + "]";
		}
	    
}
