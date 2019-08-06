package com.diviso.graeshoppe.order.repository;

import com.diviso.graeshoppe.order.domain.AuxilaryOrderLine;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AuxilaryOrderLine entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuxilaryOrderLineRepository extends JpaRepository<AuxilaryOrderLine, Long> {

}
