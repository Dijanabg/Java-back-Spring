package com.example.iktpreobuka.dataaccess.entities;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Version;


@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class AddressEntity {
	
	@JsonIgnore
	@OneToMany(mappedBy = "address", fetch = FetchType.LAZY, cascade = {
	CascadeType.REFRESH })
	private List<UserEntity> users = new ArrayList<UserEntity>();
	
	public List<UserEntity> getUsers() {
		return users;
	}

	public void setUsers(List<UserEntity> users) {
		this.users = users;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	
	@Version
	private Integer version;
	
	@Column(nullable = false)
	private String street;
	
	@Column(nullable = false)
	private String city;
	
	@Column(nullable = false)
	private String country;

	public AddressEntity() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AddressEntity(Integer id, Integer version, String street, String city, String country) {
		super();
		this.id = id;
		this.version = version;
		this.street = street;
		this.city = city;
		this.country = country;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		return "AddressEntity [id=" + id + ", version=" + version + ", street=" + street + ", city=" + city
				+ ", country=" + country + "]";
	}
	
	
}

