package com.iktpreobuka.uploadexample.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
	@Column(nullable = false)
    private String name;
	@Column(nullable = false)
    private String email;
	@Column(nullable = false)
    private Integer costs;
	@Column(nullable = false)
    private String city;
	
    public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public UserEntity() {
		super();
	}

	public UserEntity(String name, String email) {
		super();
		this.name = name;
		this.email = email;
	}

	public Integer getCosts() {
		return costs;
	}

	public void setCosts(Integer costs) {
		this.costs = costs;
	}


	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

	@Override
	public String toString() {
		return "UserEntity [id=" + id + ", name=" + name + ", email=" + email + ", costs=" + costs + ", city=" + city
				+ "]";
	}
    
}

