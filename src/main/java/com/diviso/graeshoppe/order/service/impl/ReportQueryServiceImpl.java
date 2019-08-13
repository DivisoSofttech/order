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
package com.diviso.graeshoppe.order.service.impl;

import static org.elasticsearch.action.search.SearchType.QUERY_THEN_FETCH;
import static org.elasticsearch.index.query.QueryBuilders.matchAllQuery;
import static org.elasticsearch.index.query.QueryBuilders.termQuery;

import java.util.List;

import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.elasticsearch.core.query.StringQuery;
import org.springframework.stereotype.Service;

import com.diviso.graeshoppe.order.client.store.domain.Store;
import com.diviso.graeshoppe.order.domain.Address;
import com.diviso.graeshoppe.order.domain.DeliveryInfo;
import com.diviso.graeshoppe.order.domain.Order;
import com.diviso.graeshoppe.order.domain.OrderLine;
import com.diviso.graeshoppe.order.service.ReportQueryService;
import com.diviso.graeshoppe.order.service.dto.ReportOrderLine;
import com.github.vanroy.springdata.jest.JestElasticsearchTemplate;
import com.github.vanroy.springdata.jest.aggregation.AggregatedPage;

import io.searchbox.client.JestClient;
import io.searchbox.core.search.aggregation.TermsAggregation;
import io.searchbox.core.search.aggregation.TermsAggregation.Entry;

/**
 * TODO Provide a detailed description here
 * 
 * @author MayaSanjeev mayabytatech, maya.k.k@lxisoft.com
 */
@Service
public class ReportQueryServiceImpl implements ReportQueryService {

	private final JestClient jestClient;
	private final JestElasticsearchTemplate elasticsearchTemplate;

	@Autowired
	ElasticsearchOperations elasticsearchOperations;

	public ReportQueryServiceImpl(JestClient jestClient, JestElasticsearchTemplate elasticsearchTemplate) {

		this.jestClient = jestClient;
		this.elasticsearchTemplate = elasticsearchTemplate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.order.service.ReportQueryService#findOrderByOrderId
	 * (java.lang.String)
	 */
	@Override
	public Order findOrderByOrderId(String orderId) {
		StringQuery stringQuery = new StringQuery(termQuery("orderId.keyword", orderId).toString());
		return elasticsearchOperations.queryForObject(stringQuery, Order.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.order.service.ReportQueryService#
	 * findOrderLinesByOrderId(java.lang.String)
	 */
	@Override
	public List<OrderLine> findOrderLinesByOrderId(String orderId) {
		StringQuery searchQuery = new StringQuery(termQuery("order.id", orderId).toString());
		return elasticsearchOperations.queryForList(searchQuery, OrderLine.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.diviso.graeshoppe.order.service.ReportQueryService#
	 * findOrderAddressByOrderId(java.lang.String)
	 */
	@Override
	public Address findOrderAddressById(Long id) {
		StringQuery stringQuery = new StringQuery(termQuery("id", id).toString());
		DeliveryInfo deliveryInfo = elasticsearchOperations.queryForObject(stringQuery, DeliveryInfo.class);
		return deliveryInfo.getDeliveryAddress();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.diviso.graeshoppe.order.service.ReportQueryService#findStoreByStoreId
	 * (java.lang.String)
	 */
	@Override
	public Store findStoreByStoreId(String storeId) {
		StringQuery stringQuery = new StringQuery(termQuery("regNo", storeId).toString());
		return elasticsearchOperations.queryForObject(stringQuery, Store.class);
	}

	
	@Override    
	public List<Entry> findOrderCountByCustomerId(Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withSearchType(QUERY_THEN_FETCH)

				.withIndices("order").withTypes("order")
				.addAggregation(AggregationBuilders.terms("customerorder").field("customerId.keyword")).build();

		AggregatedPage<Order> result = elasticsearchTemplate.queryForPage(searchQuery, Order.class);
		TermsAggregation categoryAggregation = result.getAggregation("customerorder", TermsAggregation.class);
		return categoryAggregation.getBuckets();

	}
	
	
	@Override
	public List<Entry> findOrderCountByCustomerIdAndStoreId(Pageable pageable) {

		SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(matchAllQuery())
				.withSearchType(QUERY_THEN_FETCH).withIndices("order").withTypes("order")
				.addAggregation(AggregationBuilders.terms("customer").field("customerId.keyword")
						.order(org.elasticsearch.search.aggregations.bucket.terms.Terms.Order.aggregation("avgPrice", true))
						.subAggregation(AggregationBuilders.avg("avgPrice").field("grandTotal"))
						.subAggregation(AggregationBuilders.terms("store").field("storeId.keyword")))
				.build();
		
		AggregatedPage<Order> result = elasticsearchTemplate.queryForPage(searchQuery, Order.class);
		
		TermsAggregation orderAgg = result.getAggregation("customer", TermsAggregation.class);
		
		orderAgg.getBuckets().forEach(bucket -> {

			double averagePrice = bucket.getAvgAggregation("avgPrice").getAvg();
			System.out.println(String.format("Key: %s, Doc count: %d, Average Price: %f", bucket.getKey(),
					bucket.getCount(), averagePrice));
			System.out.println("SSSSSSSSSSSSSSSSSS"
					+ bucket.getAggregation("store", TermsAggregation.class).getBuckets().get(1).getKeyAsString());
			System.out.println(
					"SSSSSSSSSSSSSSSSSS" + bucket.getAggregation("store", TermsAggregation.class).getBuckets().size());
		});
		return orderAgg.getBuckets();
		
	}
}
