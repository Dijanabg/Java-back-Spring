package com.iktpreobuka.project.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

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

@Entity
@Table(name = "users")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserEntity {
	
		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    private Integer id;
		@Column(name = "first_name", nullable = false)
	    private String firstName;
		@Column(name = "last_name", nullable = false)
	    private String lastName;
		@Column(name = "user_name", nullable = true)
	    private String username;
		@Column(name = "password", nullable = false)
	    private String password;
	    @Column(name = "email", nullable = false, unique = true)
	    private String email;
	    @Enumerated(EnumType.STRING)
	    @Column(name = "user_role", nullable = false)
	    private EUserRole userRole;
	    
	    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	    private List<OfferEntity> offers = new ArrayList<>();
	    
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
