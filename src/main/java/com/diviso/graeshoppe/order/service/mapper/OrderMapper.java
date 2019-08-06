package com.diviso.graeshoppe.order.service.mapper;

import com.diviso.graeshoppe.order.domain.*;
import com.diviso.graeshoppe.order.service.dto.OrderDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Order and its DTO OrderDTO.
 */
@Mapper(componentModel = "spring", uses = {DeliveryInfoMapper.class, StatusMapper.class})
public interface OrderMapper extends EntityMapper<OrderDTO, Order> {

    @Override
	@Mapping(source = "deliveryInfo.id", target = "deliveryInfoId")
    @Mapping(source = "status.id", target = "statusId")
    OrderDTO toDto(Order order);

    @Override
	@Mapping(source = "deliveryInfoId", target = "deliveryInfo")
    @Mapping(target = "orderLines", ignore = true)
    @Mapping(source = "statusId", target = "status")
    Order toEntity(OrderDTO orderDTO);

    default Order fromId(Long id) {
        if (id == null) {
            return null;
        }
        Order order = new Order();
        order.setId(id);
        return order;
    }
}
