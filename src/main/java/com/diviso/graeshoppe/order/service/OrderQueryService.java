package com.diviso.graeshoppe.order.service;

import com.diviso.graeshoppe.order.service.dto.OrderDTO;

public interface OrderQueryService {

	
	public OrderDTO findByOrderId(String orderId);
	
	
}
