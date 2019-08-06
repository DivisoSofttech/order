package com.diviso.graeshoppe.order.service.impl;

import com.diviso.graeshoppe.order.service.UniqueOrderIDService;
import com.diviso.graeshoppe.order.domain.UniqueOrderID;
import com.diviso.graeshoppe.order.repository.UniqueOrderIDRepository;
import com.diviso.graeshoppe.order.repository.search.UniqueOrderIDSearchRepository;
import com.diviso.graeshoppe.order.service.dto.UniqueOrderIDDTO;
import com.diviso.graeshoppe.order.service.mapper.UniqueOrderIDMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing UniqueOrderID.
 */
@Service
@Transactional
public class UniqueOrderIDServiceImpl implements UniqueOrderIDService {

    private final Logger log = LoggerFactory.getLogger(UniqueOrderIDServiceImpl.class);

    private final UniqueOrderIDRepository uniqueOrderIDRepository;

    private final UniqueOrderIDMapper uniqueOrderIDMapper;

    private final UniqueOrderIDSearchRepository uniqueOrderIDSearchRepository;

    public UniqueOrderIDServiceImpl(UniqueOrderIDRepository uniqueOrderIDRepository, UniqueOrderIDMapper uniqueOrderIDMapper, UniqueOrderIDSearchRepository uniqueOrderIDSearchRepository) {
        this.uniqueOrderIDRepository = uniqueOrderIDRepository;
        this.uniqueOrderIDMapper = uniqueOrderIDMapper;
        this.uniqueOrderIDSearchRepository = uniqueOrderIDSearchRepository;
    }

    /**
     * Save a uniqueOrderID.
     *
     * @param uniqueOrderIDDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UniqueOrderIDDTO save(UniqueOrderIDDTO uniqueOrderIDDTO) {
        log.debug("Request to save UniqueOrderID : {}", uniqueOrderIDDTO);
        UniqueOrderID uniqueOrderID = uniqueOrderIDMapper.toEntity(uniqueOrderIDDTO);
        uniqueOrderID = uniqueOrderIDRepository.save(uniqueOrderID);
        UniqueOrderIDDTO result = uniqueOrderIDMapper.toDto(uniqueOrderID);
        uniqueOrderIDSearchRepository.save(uniqueOrderID);
        return result;
    }

    /**
     * Get all the uniqueOrderIDS.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UniqueOrderIDDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UniqueOrderIDS");
        return uniqueOrderIDRepository.findAll(pageable)
            .map(uniqueOrderIDMapper::toDto);
    }


    /**
     * Get one uniqueOrderID by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<UniqueOrderIDDTO> findOne(Long id) {
        log.debug("Request to get UniqueOrderID : {}", id);
        return uniqueOrderIDRepository.findById(id)
            .map(uniqueOrderIDMapper::toDto);
    }

    /**
     * Delete the uniqueOrderID by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UniqueOrderID : {}", id);
        uniqueOrderIDRepository.deleteById(id);
        uniqueOrderIDSearchRepository.deleteById(id);
    }

    /**
     * Search for the uniqueOrderID corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UniqueOrderIDDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of UniqueOrderIDS for query {}", query);
        return uniqueOrderIDSearchRepository.search(queryStringQuery(query), pageable)
            .map(uniqueOrderIDMapper::toDto);
    }
}
