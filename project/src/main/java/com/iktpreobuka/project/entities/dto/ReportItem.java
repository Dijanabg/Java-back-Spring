package com.iktpreobuka.project.entities.dto;

import java.time.LocalDate;

public class ReportItem {
	private LocalDate date; // Datum za koji se vrši računanje zarade
    private Double income; // Zarada za dati datum
    private Integer numberOfOffers; // Broj prodatih ponuda te kategorije
	public ReportItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public Double getIncome() {
		return income;
	}
	public void setIncome(Double income) {
		this.income = income;
	}
	public Integer getNumberOfOffers() {
		return numberOfOffers;
	}
	public void setNumberOfOffers(Integer numberOfOffers) {
		this.numberOfOffers = numberOfOffers;
	}
	@Override
	public String toString() {
		return "ReportItem [date=" + date + ", income=" + income + ", numberOfOffers=" + numberOfOffers + "]";
	}
    
    
}
