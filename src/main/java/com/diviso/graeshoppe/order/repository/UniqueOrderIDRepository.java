package com.diviso.graeshoppe.order.repository;

import com.diviso.graeshoppe.order.domain.UniqueOrderID;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the UniqueOrderID entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UniqueOrderIDRepository extends JpaRepository<UniqueOrderID, Long> {

}
