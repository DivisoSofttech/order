package com.diviso.graeshoppe.order.service;

import com.diviso.graeshoppe.order.service.dto.AuxilaryOrderLineDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AuxilaryOrderLine.
 */
public interface AuxilaryOrderLineService {

    /**
     * Save a auxilaryOrderLine.
     *
     * @param auxilaryOrderLineDTO the entity to save
     * @return the persisted entity
     */
    AuxilaryOrderLineDTO save(AuxilaryOrderLineDTO auxilaryOrderLineDTO);

    /**
     * Get all the auxilaryOrderLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AuxilaryOrderLineDTO> findAll(Pageable pageable);


    /**
     * Get the "id" auxilaryOrderLine.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AuxilaryOrderLineDTO> findOne(Long id);

    /**
     * Delete the "id" auxilaryOrderLine.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the auxilaryOrderLine corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AuxilaryOrderLineDTO> search(String query, Pageable pageable);
}
