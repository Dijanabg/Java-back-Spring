package com.iktpreobuka.project.mappers;

import org.springframework.stereotype.Component;

import com.iktpreobuka.project.entities.VoucherEntity;
import com.iktpreobuka.project.entities.dto.VoucherDTO;

@Component
public class VoucherMapper {
	public  VoucherEntity toEntity(VoucherDTO dto) {
		VoucherEntity entity = new VoucherEntity();
        entity.setExpirationDate(dto.getExpirationDate());
        entity.setIsUsed(dto.getIsUsed());
        return entity;
    }
	
	public  VoucherDTO entityToDto(VoucherEntity entity) {
		VoucherDTO dto = new VoucherDTO();
        dto.setExpirationDate(entity.getExpirationDate());
        dto.setIsUsed(entity.getIsUsed());
        return dto;
    }
}
