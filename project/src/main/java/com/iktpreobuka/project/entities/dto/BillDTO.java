package com.iktpreobuka.project.entities.dto;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;

public class BillDTO {
	
	@NotNull(message = "Payment made status cannot be null")
	private Boolean paymentMade=false;
	@NotNull(message = "Payment made status cannot be null")
    private Boolean paymentCanceled=false;
	@JsonFormat(
			shape = JsonFormat.Shape.STRING,
			pattern = "dd-MM-yyyy HH:mm:ss")
	@NotNull(message = "Bill made date cannot be null")
	private LocalDateTime billCreated;

	public BillDTO() {
		super();
		// TODO Auto-generated constructor stub
	}

	
	
	public BillDTO(
			@NotNull(message = "Payment made status cannot be null") Boolean paymentMade,
			@NotNull(message = "Payment made status cannot be null") Boolean paymentCanceled,
			@NotNull(message = "Bill made date cannot be null") LocalDateTime billCreated) {
		super();
		this.paymentMade = paymentMade;
		this.paymentCanceled = paymentCanceled;
		this.billCreated = billCreated;
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
		return "BillDTO [paymentMade=" + paymentMade + ", paymentCanceled=" + paymentCanceled + ", billCreated="
				+ billCreated + "]";
	}

	
}
