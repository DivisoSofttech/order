 /*
 * Copyright 2002-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.diviso.graeshoppe.order.service;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.diviso.graeshoppe.order.client.store.domain.Store;
import com.diviso.graeshoppe.order.domain.Address;
import com.diviso.graeshoppe.order.domain.Order;
import com.diviso.graeshoppe.order.domain.OrderLine;
import com.diviso.graeshoppe.order.service.dto.Product;
import com.diviso.graeshoppe.order.service.dto.ReportOrderLine;

import io.searchbox.core.search.aggregation.TermsAggregation.Entry;

/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public interface ReportQueryService {

	/**
	 * @param orderId
	 * @return
	 */
	Order findOrderByOrderId(String orderId);

	/**
	 * @param orderId
	 * @return
	 */
	List<OrderLine> findOrderLinesByOrderId(String orderId);

	/**
	 * @param long1
	 * @return
	 */
	Address findOrderAddressById(Long long1);

	/**
	 * @param storeId
	 * @return
	 */
	Store findStoreByStoreId(String storeId);

	/**
	 * @param pageable
	 * @return
	 */
	List<Entry> findOrderCountByCustomerId(Pageable pageable);

	/**
	 * @param pageable
	 * @return
	 */
	List<Entry> findOrderCountByCustomerIdAndStoreId(Pageable pageable);

	/**
	 * @param productId
	 * @return
	 */
	Product findProductByProductId(Long productId);

}
