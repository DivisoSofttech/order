package com.diviso.graeshoppe.order.repository.search;

import com.diviso.graeshoppe.order.domain.AuxilaryOrderLine;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the AuxilaryOrderLine entity.
 */
public interface AuxilaryOrderLineSearchRepository extends ElasticsearchRepository<AuxilaryOrderLine, Long> {
}
