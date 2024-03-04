package com.iktpreobuka.project.entities.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public class VoucherDTO {
	 
		@NotNull(message = "datum isteka ne sme biti null")
		@JsonFormat(
				shape = JsonFormat.Shape.STRING,
				pattern = "dd-MM-yyyy")
	    @FutureOrPresent(message = "Datum isteka ne sme bitiu proslosti")
	    private LocalDate expirationDate;

	    @NotNull(message = "Ne sme biti null")
	    private Boolean isUsed = false; 

	    public VoucherDTO() {
	        super();
	    }

	    public VoucherDTO(LocalDate expirationDate, Boolean isUsed) {
	        super();
	        this.expirationDate = expirationDate;
	        this.isUsed = isUsed;
	    }

	    public LocalDate getExpirationDate() {
	        return expirationDate;
	    }

	    public void setExpirationDate(LocalDate expirationDate) {
	        this.expirationDate = expirationDate;
	    }

	    public Boolean getIsUsed() {
	        return isUsed;
	    }

	    public void setIsUsed(Boolean isUsed) {
	        this.isUsed = isUsed;
	    }
	}
