package com.diviso.graeshoppe.order.repository.search;

import com.diviso.graeshoppe.order.domain.DeliveryInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the DeliveryInfo entity.
 */
public interface DeliveryInfoSearchRepository extends ElasticsearchRepository<DeliveryInfo, Long> {
}
