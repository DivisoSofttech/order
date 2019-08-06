package com.diviso.graeshoppe.order.service.mapper;

import com.diviso.graeshoppe.order.domain.*;
import com.diviso.graeshoppe.order.service.dto.UniqueOrderIDDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UniqueOrderID and its DTO UniqueOrderIDDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UniqueOrderIDMapper extends EntityMapper<UniqueOrderIDDTO, UniqueOrderID> {



    default UniqueOrderID fromId(Long id) {
        if (id == null) {
            return null;
        }
        UniqueOrderID uniqueOrderID = new UniqueOrderID();
        uniqueOrderID.setId(id);
        return uniqueOrderID;
    }
}
