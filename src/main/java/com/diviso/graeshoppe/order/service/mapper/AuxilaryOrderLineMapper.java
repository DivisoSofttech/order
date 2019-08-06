package com.diviso.graeshoppe.order.service.mapper;

import com.diviso.graeshoppe.order.domain.*;
import com.diviso.graeshoppe.order.service.dto.AuxilaryOrderLineDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity AuxilaryOrderLine and its DTO AuxilaryOrderLineDTO.
 */
@Mapper(componentModel = "spring", uses = {OrderLineMapper.class})
public interface AuxilaryOrderLineMapper extends EntityMapper<AuxilaryOrderLineDTO, AuxilaryOrderLine> {

    @Override
	@Mapping(source = "orderLine.id", target = "orderLineId")
    AuxilaryOrderLineDTO toDto(AuxilaryOrderLine auxilaryOrderLine);

    @Override
	@Mapping(source = "orderLineId", target = "orderLine")
    AuxilaryOrderLine toEntity(AuxilaryOrderLineDTO auxilaryOrderLineDTO);

    default AuxilaryOrderLine fromId(Long id) {
        if (id == null) {
            return null;
        }
        AuxilaryOrderLine auxilaryOrderLine = new AuxilaryOrderLine();
        auxilaryOrderLine.setId(id);
        return auxilaryOrderLine;
    }
}
