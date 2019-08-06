package com.diviso.graeshoppe.order.web.rest;

import com.diviso.graeshoppe.order.OrderApp;

import com.diviso.graeshoppe.order.domain.UniqueOrderID;
import com.diviso.graeshoppe.order.repository.UniqueOrderIDRepository;
import com.diviso.graeshoppe.order.repository.search.UniqueOrderIDSearchRepository;
import com.diviso.graeshoppe.order.service.UniqueOrderIDService;
import com.diviso.graeshoppe.order.service.dto.UniqueOrderIDDTO;
import com.diviso.graeshoppe.order.service.mapper.UniqueOrderIDMapper;
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
 * Test class for the UniqueOrderIDResource REST controller.
 *
 * @see UniqueOrderIDResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = OrderApp.class)
public class UniqueOrderIDResourceIntTest {

    @Autowired
    private UniqueOrderIDRepository uniqueOrderIDRepository;

    @Autowired
    private UniqueOrderIDMapper uniqueOrderIDMapper;

    @Autowired
    private UniqueOrderIDService uniqueOrderIDService;

    /**
     * This repository is mocked in the com.diviso.graeshoppe.order.repository.search test package.
     *
     * @see com.diviso.graeshoppe.order.repository.search.UniqueOrderIDSearchRepositoryMockConfiguration
     */
    @Autowired
    private UniqueOrderIDSearchRepository mockUniqueOrderIDSearchRepository;

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

    private MockMvc restUniqueOrderIDMockMvc;

    private UniqueOrderID uniqueOrderID;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UniqueOrderIDResource uniqueOrderIDResource = new UniqueOrderIDResource(uniqueOrderIDService);
        this.restUniqueOrderIDMockMvc = MockMvcBuilders.standaloneSetup(uniqueOrderIDResource)
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
    public static UniqueOrderID createEntity(EntityManager em) {
        UniqueOrderID uniqueOrderID = new UniqueOrderID();
        return uniqueOrderID;
    }

    @Before
    public void initTest() {
        uniqueOrderID = createEntity(em);
    }

    @Test
    @Transactional
    public void createUniqueOrderID() throws Exception {
        int databaseSizeBeforeCreate = uniqueOrderIDRepository.findAll().size();

        // Create the UniqueOrderID
        UniqueOrderIDDTO uniqueOrderIDDTO = uniqueOrderIDMapper.toDto(uniqueOrderID);
        restUniqueOrderIDMockMvc.perform(post("/api/unique-order-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueOrderIDDTO)))
            .andExpect(status().isCreated());

        // Validate the UniqueOrderID in the database
        List<UniqueOrderID> uniqueOrderIDList = uniqueOrderIDRepository.findAll();
        assertThat(uniqueOrderIDList).hasSize(databaseSizeBeforeCreate + 1);
        UniqueOrderID testUniqueOrderID = uniqueOrderIDList.get(uniqueOrderIDList.size() - 1);

        // Validate the UniqueOrderID in Elasticsearch
        verify(mockUniqueOrderIDSearchRepository, times(1)).save(testUniqueOrderID);
    }

    @Test
    @Transactional
    public void createUniqueOrderIDWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = uniqueOrderIDRepository.findAll().size();

        // Create the UniqueOrderID with an existing ID
        uniqueOrderID.setId(1L);
        UniqueOrderIDDTO uniqueOrderIDDTO = uniqueOrderIDMapper.toDto(uniqueOrderID);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUniqueOrderIDMockMvc.perform(post("/api/unique-order-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueOrderIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniqueOrderID in the database
        List<UniqueOrderID> uniqueOrderIDList = uniqueOrderIDRepository.findAll();
        assertThat(uniqueOrderIDList).hasSize(databaseSizeBeforeCreate);

        // Validate the UniqueOrderID in Elasticsearch
        verify(mockUniqueOrderIDSearchRepository, times(0)).save(uniqueOrderID);
    }

    @Test
    @Transactional
    public void getAllUniqueOrderIDS() throws Exception {
        // Initialize the database
        uniqueOrderIDRepository.saveAndFlush(uniqueOrderID);

        // Get all the uniqueOrderIDList
        restUniqueOrderIDMockMvc.perform(get("/api/unique-order-ids?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniqueOrderID.getId().intValue())));
    }
    
    @Test
    @Transactional
    public void getUniqueOrderID() throws Exception {
        // Initialize the database
        uniqueOrderIDRepository.saveAndFlush(uniqueOrderID);

        // Get the uniqueOrderID
        restUniqueOrderIDMockMvc.perform(get("/api/unique-order-ids/{id}", uniqueOrderID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(uniqueOrderID.getId().intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingUniqueOrderID() throws Exception {
        // Get the uniqueOrderID
        restUniqueOrderIDMockMvc.perform(get("/api/unique-order-ids/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUniqueOrderID() throws Exception {
        // Initialize the database
        uniqueOrderIDRepository.saveAndFlush(uniqueOrderID);

        int databaseSizeBeforeUpdate = uniqueOrderIDRepository.findAll().size();

        // Update the uniqueOrderID
        UniqueOrderID updatedUniqueOrderID = uniqueOrderIDRepository.findById(uniqueOrderID.getId()).get();
        // Disconnect from session so that the updates on updatedUniqueOrderID are not directly saved in db
        em.detach(updatedUniqueOrderID);
        UniqueOrderIDDTO uniqueOrderIDDTO = uniqueOrderIDMapper.toDto(updatedUniqueOrderID);

        restUniqueOrderIDMockMvc.perform(put("/api/unique-order-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueOrderIDDTO)))
            .andExpect(status().isOk());

        // Validate the UniqueOrderID in the database
        List<UniqueOrderID> uniqueOrderIDList = uniqueOrderIDRepository.findAll();
        assertThat(uniqueOrderIDList).hasSize(databaseSizeBeforeUpdate);
        UniqueOrderID testUniqueOrderID = uniqueOrderIDList.get(uniqueOrderIDList.size() - 1);

        // Validate the UniqueOrderID in Elasticsearch
        verify(mockUniqueOrderIDSearchRepository, times(1)).save(testUniqueOrderID);
    }

    @Test
    @Transactional
    public void updateNonExistingUniqueOrderID() throws Exception {
        int databaseSizeBeforeUpdate = uniqueOrderIDRepository.findAll().size();

        // Create the UniqueOrderID
        UniqueOrderIDDTO uniqueOrderIDDTO = uniqueOrderIDMapper.toDto(uniqueOrderID);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restUniqueOrderIDMockMvc.perform(put("/api/unique-order-ids")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(uniqueOrderIDDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UniqueOrderID in the database
        List<UniqueOrderID> uniqueOrderIDList = uniqueOrderIDRepository.findAll();
        assertThat(uniqueOrderIDList).hasSize(databaseSizeBeforeUpdate);

        // Validate the UniqueOrderID in Elasticsearch
        verify(mockUniqueOrderIDSearchRepository, times(0)).save(uniqueOrderID);
    }

    @Test
    @Transactional
    public void deleteUniqueOrderID() throws Exception {
        // Initialize the database
        uniqueOrderIDRepository.saveAndFlush(uniqueOrderID);

        int databaseSizeBeforeDelete = uniqueOrderIDRepository.findAll().size();

        // Delete the uniqueOrderID
        restUniqueOrderIDMockMvc.perform(delete("/api/unique-order-ids/{id}", uniqueOrderID.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UniqueOrderID> uniqueOrderIDList = uniqueOrderIDRepository.findAll();
        assertThat(uniqueOrderIDList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the UniqueOrderID in Elasticsearch
        verify(mockUniqueOrderIDSearchRepository, times(1)).deleteById(uniqueOrderID.getId());
    }

    @Test
    @Transactional
    public void searchUniqueOrderID() throws Exception {
        // Initialize the database
        uniqueOrderIDRepository.saveAndFlush(uniqueOrderID);
        when(mockUniqueOrderIDSearchRepository.search(queryStringQuery("id:" + uniqueOrderID.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(uniqueOrderID), PageRequest.of(0, 1), 1));
        // Search the uniqueOrderID
        restUniqueOrderIDMockMvc.perform(get("/api/_search/unique-order-ids?query=id:" + uniqueOrderID.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(uniqueOrderID.getId().intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniqueOrderID.class);
        UniqueOrderID uniqueOrderID1 = new UniqueOrderID();
        uniqueOrderID1.setId(1L);
        UniqueOrderID uniqueOrderID2 = new UniqueOrderID();
        uniqueOrderID2.setId(uniqueOrderID1.getId());
        assertThat(uniqueOrderID1).isEqualTo(uniqueOrderID2);
        uniqueOrderID2.setId(2L);
        assertThat(uniqueOrderID1).isNotEqualTo(uniqueOrderID2);
        uniqueOrderID1.setId(null);
        assertThat(uniqueOrderID1).isNotEqualTo(uniqueOrderID2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UniqueOrderIDDTO.class);
        UniqueOrderIDDTO uniqueOrderIDDTO1 = new UniqueOrderIDDTO();
        uniqueOrderIDDTO1.setId(1L);
        UniqueOrderIDDTO uniqueOrderIDDTO2 = new UniqueOrderIDDTO();
        assertThat(uniqueOrderIDDTO1).isNotEqualTo(uniqueOrderIDDTO2);
        uniqueOrderIDDTO2.setId(uniqueOrderIDDTO1.getId());
        assertThat(uniqueOrderIDDTO1).isEqualTo(uniqueOrderIDDTO2);
        uniqueOrderIDDTO2.setId(2L);
        assertThat(uniqueOrderIDDTO1).isNotEqualTo(uniqueOrderIDDTO2);
        uniqueOrderIDDTO1.setId(null);
        assertThat(uniqueOrderIDDTO1).isNotEqualTo(uniqueOrderIDDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(uniqueOrderIDMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(uniqueOrderIDMapper.fromId(null)).isNull();
    }
}
