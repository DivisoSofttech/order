package com.diviso.graeshoppe.order.service;

import com.diviso.graeshoppe.order.models.AcceptOrderRequest;
import com.diviso.graeshoppe.order.models.ProcessPaymentRequest;
import com.diviso.graeshoppe.order.resource.assembler.CommandResource;
import com.diviso.graeshoppe.order.service.dto.NotificationDTO;
import com.diviso.graeshoppe.order.service.dto.OrderDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Order.
 */
public interface OrderCommandService {

    /**
     * Save a order.
     *
     * @param orderDTO the entity to save
     * @return the persisted entity
     */
    CommandResource save(OrderDTO orderDTO);

    /**
     * Get all the orders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderDTO> findAll(Pageable pageable);


    /**
     * Get the "id" order.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderDTO> findOne(Long id);

    /**
     * Delete the "id" order.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the order corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderDTO> search(String query, Pageable pageable);

	CommandResource processPayment(ProcessPaymentRequest processPaymentRequest);

	CommandResource acceptOrder(AcceptOrderRequest acceptOrderRequest);

	NotificationDTO sendNotification(String orderId);

	OrderDTO update(OrderDTO orderDTO);
}
