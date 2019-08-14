package com.diviso.graeshoppe.order.web.rest;
import com.diviso.graeshoppe.order.models.AcceptOrderRequest;
import com.diviso.graeshoppe.order.models.ProcessPaymentRequest;
import com.diviso.graeshoppe.order.resource.assembler.CommandResource;
import com.diviso.graeshoppe.order.service.OrderCommandService;
import com.diviso.graeshoppe.order.web.rest.errors.BadRequestAlertException;
import com.diviso.graeshoppe.order.web.rest.util.HeaderUtil;
import com.diviso.graeshoppe.order.web.rest.util.PaginationUtil;
import com.diviso.graeshoppe.order.service.dto.NotificationDTO;
import com.diviso.graeshoppe.order.service.dto.OrderDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Order.
 */
@RestController
@RequestMapping("/api")
public class OrderCommandResource {

	@Autowired
	private  SimpMessagingTemplate template;
	

    private final Logger log = LoggerFactory.getLogger(OrderCommandResource.class);

    private static final String ENTITY_NAME = "orderOrder";

    private final OrderCommandService orderService;
    
    @GetMapping("/send")
    public void send(Principal principal) {
    	log.info("User is #################################### "+principal.getName());
    	template.convertAndSendToUser(principal.getName(), "/queue/notification", "Message from "+principal.getName());
   
    	
    }

    public OrderCommandResource(OrderCommandService orderService) {
        this.orderService = orderService;
    }

    /**
     * POST  /orders : Create a new order.
     *
     * @param orderDTO the orderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new orderDTO, or with status 400 (Bad Request) if the order has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/orders")
    public ResponseEntity<CommandResource> createOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
		orderDTO.setStatusId(1l);
        log.debug("REST request to save Order : {}", orderDTO);
        if (orderDTO.getId() != null) {
            throw new BadRequestAlertException("A new order cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CommandResource result = orderService.save(orderDTO);
        return ResponseEntity.created(new URI("/api/orders/" + result.getNextTaskId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getNextTaskId().toString()))
            .body(result);
    }

    /**
     * PUT  /orders : Updates an existing order.
     *
     * @param orderDTO the orderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated orderDTO,
     * or with status 400 (Bad Request) if the orderDTO is not valid,
     * or with status 500 (Internal Server Error) if the orderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/orders")
    public ResponseEntity<OrderDTO> updateOrder(@RequestBody OrderDTO orderDTO) throws URISyntaxException {
        log.debug("REST request to update Order : {}", orderDTO);
        if (orderDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderDTO result = orderService.update(orderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, orderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /orders : get all the orders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of orders in body
     */
    @GetMapping("/orders")
    public ResponseEntity<List<OrderDTO>> getAllOrders(Pageable pageable) {
        log.debug("REST request to get a page of Orders");
        Page<OrderDTO> page = orderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /orders/:id : get the "id" order.
     *
     * @param id the id of the orderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the orderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrder(@PathVariable Long id) {
        log.debug("REST request to get Order : {}", id);
        Optional<OrderDTO> orderDTO = orderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(orderDTO);
    }

    /**
     * DELETE  /orders/:id : delete the "id" order.
     *
     * @param id the id of the orderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        log.debug("REST request to delete Order : {}", id);
        orderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/orders?query=:query : search for the order corresponding
     * to the query.
     *
     * @param query the query of the order search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/orders")
    public ResponseEntity<List<OrderDTO>> searchOrders(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Orders for query {}", query);
        Page<OrderDTO> page = orderService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/orders");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    @PostMapping("/processPayment")
    public CommandResource processPayment(@RequestBody ProcessPaymentRequest processPaymentRequest) {
		log.info("Request to process the payment "+processPaymentRequest);
    	return orderService.processPayment(processPaymentRequest);
    	
    }
    
    @PostMapping("/acceptOrder")
    public CommandResource acceptOrder(@RequestBody AcceptOrderRequest acceptOrderRequest) {
    	log.info("Request to accept the order "+acceptOrderRequest);
    	return orderService.acceptOrder(acceptOrderRequest);
    }
    
    @PostMapping("/sendNotification/{orderId}")
    public NotificationDTO sendNotification(@PathVariable String orderId) {
    	return orderService.sendNotification(orderId);
    }
}
