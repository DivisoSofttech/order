package com.diviso.graeshoppe.order.web.rest;

import com.diviso.graeshoppe.order.OrderApp;

import com.diviso.graeshoppe.order.domain.AuxilaryOrderLine;
import com.diviso.graeshoppe.order.repository.AuxilaryOrderLineRepository;
import com.diviso.graeshoppe.order.repository.search.AuxilaryOrderLineSearchRepository;
import com.diviso.graeshoppe.order.service.AuxilaryOrderLineService;
import com.diviso.graeshoppe.order.service.dto.AuxilaryOrderLineDTO;
import com.diviso.graeshoppe.order.service.mapper.AuxilaryOrderLineMapper;
import com.diviso.graeshoppe.order.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;


import static com.diviso.graeshoppe.order.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the AuxilaryOrderLineResource REST controller.
 *
 * @see AuxilaryOrderLineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApp.class)
public class AuxilaryOrderLineResourceIntTest {

    @Autowired
    private AuxilaryOrderLineRepository auxilaryOrderLineRepository;

    @Autowired
    private AuxilaryOrderLineMapper auxilaryOrderLineMapper;

    @Autowired
    private AuxilaryOrderLineService auxilaryOrderLineService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.order.repository.search test package.
     *
     * @see com.diviso.graeshoppe.order.repository.search.AuxilaryOrderLineSearchRepositoryMockConfiguration
     */
    @Autowired
    private AuxilaryOrderLineSearchRepository mockAuxilaryOrderLineSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restAuxilaryOrderLineMockMvc;

    private AuxilaryOrderLine auxilaryOrderLine;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AuxilaryOrderLineResource auxilaryOrderLineResource = new AuxilaryOrderLineResource(auxilaryOrderLineService);
        this.restAuxilaryOrderLineMockMvc = MockMvcBuilders.standaloneSetup(auxilaryOrderLineResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AuxilaryOrderLine createEntity(EntityManager em) {
        AuxilaryOrderLine auxilaryOrderLine = new AuxilaryOrderLine();
        return auxilaryOrderLine;
    }

    @Before
    public void initTest() {
        auxilaryOrderLine = createEntity(em);
    }

    @Test
    @Transactional
    public void createAuxilaryOrderLine() throws Exception {
        int databaseSizeBeforeCreate = auxilaryOrderLineRepository.findAll().size();

        // Create the AuxilaryOrderLine
        AuxilaryOrderLineDTO auxilaryOrderLineDTO = auxilaryOrderLineMapper.toDto(auxilaryOrderLine);
        restAuxilaryOrderLineMockMvc.perform(post("/api/auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryOrderLineDTO)))
            .andExpect(status().isCreated());

        // Validate the AuxilaryOrderLine in the database
        List<AuxilaryOrderLine> auxilaryOrderLineList = auxilaryOrderLineRepository.findAll();
        assertThat(auxilaryOrderLineList).hasSize(databaseSizeBeforeCreate + 1);
        AuxilaryOrderLine testAuxilaryOrderLine = auxilaryOrderLineList.get(auxilaryOrderLineList.size() - 1);

        // Validate the AuxilaryOrderLine in Elasticsearch
        verify(mockAuxilaryOrderLineSearchRepository, times(1)).save(testAuxilaryOrderLine);
    }

    @Test
    @Transactional
    public void createAuxilaryOrderLineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = auxilaryOrderLineRepository.findAll().size();

        // Create the AuxilaryOrderLine with an existing ID
        auxilaryOrderLine.setId(1L);
        AuxilaryOrderLineDTO auxilaryOrderLineDTO = auxilaryOrderLineMapper.toDto(auxilaryOrderLine);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAuxilaryOrderLineMockMvc.perform(post("/api/auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuxilaryOrderLine in the database
        List<AuxilaryOrderLine> auxilaryOrderLineList = auxilaryOrderLineRepository.findAll();
        assertThat(auxilaryOrderLineList).hasSize(databaseSizeBeforeCreate);

        // Validate the AuxilaryOrderLine in Elasticsearch
        verify(mockAuxilaryOrderLineSearchRepository, times(0)).save(auxilaryOrderLine);
    }

    @Test
    @Transactional
    public void getAllAuxilaryOrderLines() throws Exception {
        // Initialize the database
        auxilaryOrderLineRepository.saveAndFlush(auxilaryOrderLine);

        // Get all the auxilaryOrderLineList
        restAuxilaryOrderLineMockMvc.perform(get("/api/auxilary-order-lines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auxilaryOrderLine.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getAuxilaryOrderLine() throws Exception {
        // Initialize the database
        auxilaryOrderLineRepository.saveAndFlush(auxilaryOrderLine);

        // Get the auxilaryOrderLine
        restAuxilaryOrderLineMockMvc.perform(get("/api/auxilary-order-lines/{id}", auxilaryOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(auxilaryOrderLine.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingAuxilaryOrderLine() throws Exception {
        // Get the auxilaryOrderLine
        restAuxilaryOrderLineMockMvc.perform(get("/api/auxilary-order-lines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAuxilaryOrderLine() throws Exception {
        // Initialize the database
        auxilaryOrderLineRepository.saveAndFlush(auxilaryOrderLine);

        int databaseSizeBeforeUpdate = auxilaryOrderLineRepository.findAll().size();

        // Update the auxilaryOrderLine
        AuxilaryOrderLine updatedAuxilaryOrderLine = auxilaryOrderLineRepository.findById(auxilaryOrderLine.getId()).get();
        // Disconnect from session so that the updates on updatedAuxilaryOrderLine are not directly saved in db
        em.detach(updatedAuxilaryOrderLine);
        AuxilaryOrderLineDTO auxilaryOrderLineDTO = auxilaryOrderLineMapper.toDto(updatedAuxilaryOrderLine);

        restAuxilaryOrderLineMockMvc.perform(put("/api/auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryOrderLineDTO)))
            .andExpect(status().isOk());

        // Validate the AuxilaryOrderLine in the database
        List<AuxilaryOrderLine> auxilaryOrderLineList = auxilaryOrderLineRepository.findAll();
        assertThat(auxilaryOrderLineList).hasSize(databaseSizeBeforeUpdate);
        AuxilaryOrderLine testAuxilaryOrderLine = auxilaryOrderLineList.get(auxilaryOrderLineList.size() - 1);

        // Validate the AuxilaryOrderLine in Elasticsearch
        verify(mockAuxilaryOrderLineSearchRepository, times(1)).save(testAuxilaryOrderLine);
    }

    @Test
    @Transactional
    public void updateNonExistingAuxilaryOrderLine() throws Exception {
        int databaseSizeBeforeUpdate = auxilaryOrderLineRepository.findAll().size();

        // Create the AuxilaryOrderLine
        AuxilaryOrderLineDTO auxilaryOrderLineDTO = auxilaryOrderLineMapper.toDto(auxilaryOrderLine);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAuxilaryOrderLineMockMvc.perform(put("/api/auxilary-order-lines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(auxilaryOrderLineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AuxilaryOrderLine in the database
        List<AuxilaryOrderLine> auxilaryOrderLineList = auxilaryOrderLineRepository.findAll();
        assertThat(auxilaryOrderLineList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AuxilaryOrderLine in Elasticsearch
        verify(mockAuxilaryOrderLineSearchRepository, times(0)).save(auxilaryOrderLine);
    }

    @Test
    @Transactional
    public void deleteAuxilaryOrderLine() throws Exception {
        // Initialize the database
        auxilaryOrderLineRepository.saveAndFlush(auxilaryOrderLine);

        int databaseSizeBeforeDelete = auxilaryOrderLineRepository.findAll().size();

        // Delete the auxilaryOrderLine
        restAuxilaryOrderLineMockMvc.perform(delete("/api/auxilary-order-lines/{id}", auxilaryOrderLine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<AuxilaryOrderLine> auxilaryOrderLineList = auxilaryOrderLineRepository.findAll();
        assertThat(auxilaryOrderLineList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AuxilaryOrderLine in Elasticsearch
        verify(mockAuxilaryOrderLineSearchRepository, times(1)).deleteById(auxilaryOrderLine.getId());
    }

    @Test
    @Transactional
    public void searchAuxilaryOrderLine() throws Exception {
        // Initialize the database
        auxilaryOrderLineRepository.saveAndFlush(auxilaryOrderLine);
        when(mockAuxilaryOrderLineSearchRepository.search(queryStringQuery("id:" + auxilaryOrderLine.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(auxilaryOrderLine), PageRequest.of(0, 1), 1));
        // Search the auxilaryOrderLine
        restAuxilaryOrderLineMockMvc.perform(get("/api/_search/auxilary-order-lines?query=id:" + auxilaryOrderLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(auxilaryOrderLine.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuxilaryOrderLine.class);
        AuxilaryOrderLine auxilaryOrderLine1 = new AuxilaryOrderLine();
        auxilaryOrderLine1.setId(1L);
        AuxilaryOrderLine auxilaryOrderLine2 = new AuxilaryOrderLine();
        auxilaryOrderLine2.setId(auxilaryOrderLine1.getId());
        assertThat(auxilaryOrderLine1).isEqualTo(auxilaryOrderLine2);
        auxilaryOrderLine2.setId(2L);
        assertThat(auxilaryOrderLine1).isNotEqualTo(auxilaryOrderLine2);
        auxilaryOrderLine1.setId(null);
        assertThat(auxilaryOrderLine1).isNotEqualTo(auxilaryOrderLine2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AuxilaryOrderLineDTO.class);
        AuxilaryOrderLineDTO auxilaryOrderLineDTO1 = new AuxilaryOrderLineDTO();
        auxilaryOrderLineDTO1.setId(1L);
        AuxilaryOrderLineDTO auxilaryOrderLineDTO2 = new AuxilaryOrderLineDTO();
        assertThat(auxilaryOrderLineDTO1).isNotEqualTo(auxilaryOrderLineDTO2);
        auxilaryOrderLineDTO2.setId(auxilaryOrderLineDTO1.getId());
        assertThat(auxilaryOrderLineDTO1).isEqualTo(auxilaryOrderLineDTO2);
        auxilaryOrderLineDTO2.setId(2L);
        assertThat(auxilaryOrderLineDTO1).isNotEqualTo(auxilaryOrderLineDTO2);
        auxilaryOrderLineDTO1.setId(null);
        assertThat(auxilaryOrderLineDTO1).isNotEqualTo(auxilaryOrderLineDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(auxilaryOrderLineMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(auxilaryOrderLineMapper.fromId(null)).isNull();
    }
}
