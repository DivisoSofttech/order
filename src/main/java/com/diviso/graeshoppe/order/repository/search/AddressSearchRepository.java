package com.diviso.graeshoppe.order.repository.search;

import com.diviso.graeshoppe.order.domain.Address;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Address entity.
 */
public interface AddressSearchRepository extends ElasticsearchRepository<Address, Long> {
}
