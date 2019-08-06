package com.diviso.graeshoppe.order.service;

import com.diviso.graeshoppe.order.service.dto.OrderLineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing OrderLine.
 */
public interface OrderLineService {

    /**
     * Save a orderLine.
     *
     * @param orderLineDTO the entity to save
     * @return the persisted entity
     */
    OrderLineDTO save(OrderLineDTO orderLineDTO);

    /**
     * Get all the orderLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderLineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" orderLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<OrderLineDTO> findOne(Long id);

    /**
     * Delete the "id" orderLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the orderLine corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OrderLineDTO> search(String query, Pageable pageable);
}
