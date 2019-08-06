package com.diviso.graeshoppe.order.service;

import com.diviso.graeshoppe.order.service.dto.UniqueOrderIDDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing UniqueOrderID.
 */
public interface UniqueOrderIDService {

    /**
     * Save a uniqueOrderID.
     *
     * @param uniqueOrderIDDTO the entity to save
     * @return the persisted entity
     */
    UniqueOrderIDDTO save(UniqueOrderIDDTO uniqueOrderIDDTO);

    /**
     * Get all the uniqueOrderIDS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UniqueOrderIDDTO> findAll(Pageable pageable);


    /**
     * Get the "id" uniqueOrderID.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<UniqueOrderIDDTO> findOne(Long id);

    /**
     * Delete the "id" uniqueOrderID.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the uniqueOrderID corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<UniqueOrderIDDTO> search(String query, Pageable pageable);
}
