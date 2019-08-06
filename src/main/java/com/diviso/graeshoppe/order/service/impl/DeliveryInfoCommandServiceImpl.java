package com.diviso.graeshoppe.order.service.impl;

import com.diviso.graeshoppe.order.service.AddressService;
import com.diviso.graeshoppe.order.service.DeliveryInfoService;
import com.diviso.graeshoppe.order.client.bpmn.api.FormsApi;
import com.diviso.graeshoppe.order.client.bpmn.api.TasksApi;
import com.diviso.graeshoppe.order.client.bpmn.model.RestFormProperty;
import com.diviso.graeshoppe.order.client.bpmn.model.SubmitFormRequest;
import com.diviso.graeshoppe.order.domain.DeliveryInfo;
import com.diviso.graeshoppe.order.repository.DeliveryInfoRepository;
import com.diviso.graeshoppe.order.repository.search.DeliveryInfoSearchRepository;
import com.diviso.graeshoppe.order.resource.assembler.CommandResource;
import com.diviso.graeshoppe.order.resource.assembler.ResourceAssembler;
import com.diviso.graeshoppe.order.service.dto.DeliveryInfoDTO;
import com.diviso.graeshoppe.order.service.mapper.DeliveryInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing DeliveryInfo.
 */
@Service
@Transactional
public class DeliveryInfoCommandServiceImpl implements DeliveryInfoService {

    private final Logger log = LoggerFactory.getLogger(DeliveryInfoCommandServiceImpl.class);

    private final DeliveryInfoRepository deliveryInfoRepository;

    @Autowired
	private AddressService addressService;

	@Autowired
	private TasksApi tasksApi;
	@Autowired
	private FormsApi formsApi;
	@Autowired
	private ResourceAssembler resourceAssembler;
    private final DeliveryInfoMapper deliveryInfoMapper;

    private final DeliveryInfoSearchRepository deliveryInfoSearchRepository;

    public DeliveryInfoCommandServiceImpl(DeliveryInfoRepository deliveryInfoRepository, DeliveryInfoMapper deliveryInfoMapper, DeliveryInfoSearchRepository deliveryInfoSearchRepository) {
        this.deliveryInfoRepository = deliveryInfoRepository;
        this.deliveryInfoMapper = deliveryInfoMapper;
        this.deliveryInfoSearchRepository = deliveryInfoSearchRepository;
    }

    /**
     * Save a deliveryInfo.
     *
     * @param deliveryInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public CommandResource save(DeliveryInfoDTO deliveryInfoDTO,String taskId) {
        log.debug("Request to save DeliveryInfo : {}", deliveryInfoDTO);
        DeliveryInfo deliveryInfo = deliveryInfoMapper.toEntity(deliveryInfoDTO);
        deliveryInfo = deliveryInfoRepository.save(deliveryInfo);
        DeliveryInfoDTO result = deliveryInfoMapper.toDto(deliveryInfo);
        deliveryInfoSearchRepository.save(deliveryInfo);
        Long phone=addressService.findOne(deliveryInfoDTO.getDeliveryAddressId()).get().getPhone();
        CommandResource commandResource=confirmDelivery(taskId,phone,deliveryInfoDTO.getDeliveryType());
        commandResource.setSelfId(result.getId());
        return commandResource;
    }
    
   
    
	public CommandResource confirmDelivery( String taskId,Long phone,String deliveryType) {
		log.info("Task id is "+taskId+ " Phone number is ^^^^^^^^^^^^^^^^^^^^^ "+phone);
		String processInstanceId = tasksApi.getTask(taskId).getBody().getProcessInstanceId();
		SubmitFormRequest formRequest = new SubmitFormRequest();
		List<RestFormProperty> properties = new ArrayList<RestFormProperty>();
		RestFormProperty phoneProperty = new RestFormProperty();
		phoneProperty.setId("phone");
		phoneProperty.setName("phone");
		phoneProperty.setType("long");
		phoneProperty.setValue(phone.toString());
		properties.add(phoneProperty);
		
		RestFormProperty deliveryTypeProperty = new RestFormProperty();
		deliveryTypeProperty.setId("deliveryType");
		deliveryTypeProperty.setName("deliveryType");
		deliveryTypeProperty.setType("string");
		deliveryTypeProperty.setValue(deliveryType);
		properties.add(deliveryTypeProperty);
		
		formRequest.setProperties(properties);
		formRequest.setAction("completed");
		formRequest.setTaskId(taskId);
		formsApi.submitForm(formRequest);
		return resourceAssembler.toResource(processInstanceId);
	}
    
	
	 @Override
	    public DeliveryInfoDTO update(DeliveryInfoDTO deliveryInfoDTO) {
	        log.debug("Request to save DeliveryInfo : {}", deliveryInfoDTO);
	        DeliveryInfo deliveryInfo = deliveryInfoMapper.toEntity(deliveryInfoDTO);
	        deliveryInfo = deliveryInfoRepository.save(deliveryInfo);
	        DeliveryInfoDTO result = deliveryInfoMapper.toDto(deliveryInfo);
	        deliveryInfoSearchRepository.save(deliveryInfo);
	        return result;
	    }

    /**
     * Get all the deliveryInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DeliveryInfos");
        return deliveryInfoRepository.findAll(pageable)
            .map(deliveryInfoMapper::toDto);
    }


    /**
     * Get one deliveryInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DeliveryInfoDTO> findOne(Long id) {
        log.debug("Request to get DeliveryInfo : {}", id);
        return deliveryInfoRepository.findById(id)
            .map(deliveryInfoMapper::toDto);
    }

    /**
     * Delete the deliveryInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DeliveryInfo : {}", id);
        deliveryInfoRepository.deleteById(id);
        deliveryInfoSearchRepository.deleteById(id);
    }

    /**
     * Search for the deliveryInfo corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DeliveryInfoDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DeliveryInfos for query {}", query);
        return deliveryInfoSearchRepository.search(queryStringQuery(query), pageable)
            .map(deliveryInfoMapper::toDto);
    }
}
