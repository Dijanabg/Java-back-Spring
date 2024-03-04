package com.iktpreobuka.project.mappers;

import org.springframework.stereotype.Component;

import com.iktpreobuka.project.entities.BillEntity;
import com.iktpreobuka.project.entities.dto.BillDTO;
@Component
public class BillMapper {
	public  BillEntity toEntity(BillDTO dto) {
        BillEntity entity = new BillEntity();
        entity.setPaymentMade(dto.getPaymentMade());
        entity.setPaymentCanceled(dto.getPaymentCanceled());
        entity.setBillCreated(dto.getBillCreated());
        return entity;
    }
	
	public  BillDTO entityToDto(BillEntity entity) {
        BillDTO dto = new BillDTO();
        dto.setBillCreated(entity.getBillCreated());
        dto.setPaymentMade(entity.getPaymentMade());
        dto.setPaymentCanceled(entity.getPaymentCanceled());
        return dto;
    }
}
