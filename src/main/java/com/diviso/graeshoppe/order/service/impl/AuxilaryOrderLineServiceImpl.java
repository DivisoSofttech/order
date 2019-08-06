package com.diviso.graeshoppe.order.service.impl;

import com.diviso.graeshoppe.order.service.AuxilaryOrderLineService;
import com.diviso.graeshoppe.order.domain.AuxilaryOrderLine;
import com.diviso.graeshoppe.order.repository.AuxilaryOrderLineRepository;
import com.diviso.graeshoppe.order.repository.search.AuxilaryOrderLineSearchRepository;
import com.diviso.graeshoppe.order.service.dto.AuxilaryOrderLineDTO;
import com.diviso.graeshoppe.order.service.mapper.AuxilaryOrderLineMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AuxilaryOrderLine.
 */
@Service
@Transactional
public class AuxilaryOrderLineServiceImpl implements AuxilaryOrderLineService {

    private final Logger log = LoggerFactory.getLogger(AuxilaryOrderLineServiceImpl.class);

    private final AuxilaryOrderLineRepository auxilaryOrderLineRepository;

    private final AuxilaryOrderLineMapper auxilaryOrderLineMapper;

    private final AuxilaryOrderLineSearchRepository auxilaryOrderLineSearchRepository;

    public AuxilaryOrderLineServiceImpl(AuxilaryOrderLineRepository auxilaryOrderLineRepository, AuxilaryOrderLineMapper auxilaryOrderLineMapper, AuxilaryOrderLineSearchRepository auxilaryOrderLineSearchRepository) {
        this.auxilaryOrderLineRepository = auxilaryOrderLineRepository;
        this.auxilaryOrderLineMapper = auxilaryOrderLineMapper;
        this.auxilaryOrderLineSearchRepository = auxilaryOrderLineSearchRepository;
    }

    /**
     * Save a auxilaryOrderLine.
     *
     * @param auxilaryOrderLineDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AuxilaryOrderLineDTO save(AuxilaryOrderLineDTO auxilaryOrderLineDTO) {
        log.debug("Request to save AuxilaryOrderLine : {}", auxilaryOrderLineDTO);
        AuxilaryOrderLine auxilaryOrderLine = auxilaryOrderLineMapper.toEntity(auxilaryOrderLineDTO);
        auxilaryOrderLine = auxilaryOrderLineRepository.save(auxilaryOrderLine);
        AuxilaryOrderLineDTO result = auxilaryOrderLineMapper.toDto(auxilaryOrderLine);
        auxilaryOrderLineSearchRepository.save(auxilaryOrderLine);
        return result;
    }

    /**
     * Get all the auxilaryOrderLines.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuxilaryOrderLineDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AuxilaryOrderLines");
        return auxilaryOrderLineRepository.findAll(pageable)
            .map(auxilaryOrderLineMapper::toDto);
    }


    /**
     * Get one auxilaryOrderLine by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AuxilaryOrderLineDTO> findOne(Long id) {
        log.debug("Request to get AuxilaryOrderLine : {}", id);
        return auxilaryOrderLineRepository.findById(id)
            .map(auxilaryOrderLineMapper::toDto);
    }

    /**
     * Delete the auxilaryOrderLine by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AuxilaryOrderLine : {}", id);
        auxilaryOrderLineRepository.deleteById(id);
        auxilaryOrderLineSearchRepository.deleteById(id);
    }

    /**
     * Search for the auxilaryOrderLine corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AuxilaryOrderLineDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AuxilaryOrderLines for query {}", query);
        return auxilaryOrderLineSearchRepository.search(queryStringQuery(query), pageable)
            .map(auxilaryOrderLineMapper::toDto);
    }
}
