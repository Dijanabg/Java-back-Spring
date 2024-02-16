package com.iktpreobuka.restexamples.entities;

import java.time.LocalDate;

public class BankClientEntity {
	protected Integer id;
	protected String name;
	protected String surname;
	protected String email;
	protected LocalDate dateOfBirth; // Datum rođenja
    protected char bonitet; // Bonitet
    protected String grad;
    
	public BankClientEntity() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BankClientEntity(Integer id, String name, String surname, String email, LocalDate dateOfBirth) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.dateOfBirth = dateOfBirth;
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
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public char getBonitet() {
		return bonitet;
	}
	public void setBonitet(char bonitet) {
		this.bonitet = bonitet;
	}
	public String getGrad() {
        return grad;
    }

    public void setGrad(String grad) {
        this.grad = grad;
    }
	@Override
	public String toString() {
		return "BankClientEntity [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email
				+ ", dateOfBirth=" + dateOfBirth + ", bonitet=" + bonitet + "]";
	}


}