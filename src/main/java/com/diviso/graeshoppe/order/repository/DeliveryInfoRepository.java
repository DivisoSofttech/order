package com.diviso.graeshoppe.order.repository;

import com.diviso.graeshoppe.order.domain.DeliveryInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DeliveryInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DeliveryInfoRepository extends JpaRepository<DeliveryInfo, Long> {

}
