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
package com.diviso.graeshoppe.order.service.dto;

import java.util.List;

/**
 * TODO Provide a detailed description here 
 * @author MayaSanjeev
 * mayabytatech, maya.k.k@lxisoft.com
 */
public class OrderMaster {

	private String storeName;
	
	private Long storePhone;
	
	private String methodOfOrder;
	
	private String dueDate;
	
	private String dueTime;
	
	private String orderNumber;
	
	private String notes;
	
	private List<ReportOrderLine> orderLine;
	
	private Double deliveryCharge;
	
	private Double ServiceCharge;
	
	private Double totalDue ;
	
	private String orderStatus;
	
	private String customerId;
	
	//...............address........
	   private Long pincode;

	    private String houseNoOrBuildingName;

	    private String roadNameAreaOrStreet;

	    private String city;

	    private String state;

	    private String landmark;

	    private String name;

	    private Long phone;

	    private Long alternatePhone;

	    private String addressType;

	
	private Long orderFromCustomer;
	
	private Long customersOrder;
	
	private String orderPlaceAt;
	
	private String orderAcceptedAt;

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public Long getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(Long storePhone) {
		this.storePhone = storePhone;
	}

	public String getMethodOfOrder() {
		return methodOfOrder;
	}

	public void setMethodOfOrder(String methodOfOrder) {
		this.methodOfOrder = methodOfOrder;
	}

	public String getDueDate() {
		return dueDate;
	}

	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}

	public String getDueTime() {
		return dueTime;
	}

	public void setDueTime(String dueTime) {
		this.dueTime = dueTime;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<ReportOrderLine> getOrderLine() {
		return orderLine;
	}

	public void setOrderLine(List<ReportOrderLine> orderLine) {
		this.orderLine = orderLine;
	}

	public Double getDeliveryCharge() {
		return deliveryCharge;
	}

	public void setDeliveryCharge(Double deliveryCharge) {
		this.deliveryCharge = deliveryCharge;
	}

	public Double getServiceCharge() {
		return ServiceCharge;
	}

	public void setServiceCharge(Double serviceCharge) {
		ServiceCharge = serviceCharge;
	}

	public Double getTotalDue() {
		return totalDue;
	}

	public void setTotalDue(Double totalDue) {
		this.totalDue = totalDue;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public Long getPincode() {
		return pincode;
	}

	public void setPincode(Long pincode) {
		this.pincode = pincode;
	}

	public String getHouseNoOrBuildingName() {
		return houseNoOrBuildingName;
	}

	public void setHouseNoOrBuildingName(String houseNoOrBuildingName) {
		this.houseNoOrBuildingName = houseNoOrBuildingName;
	}

	public String getRoadNameAreaOrStreet() {
		return roadNameAreaOrStreet;
	}

	public void setRoadNameAreaOrStreet(String roadNameAreaOrStreet) {
		this.roadNameAreaOrStreet = roadNameAreaOrStreet;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getLandmark() {
		return landmark;
	}

	public void setLandmark(String landmark) {
		this.landmark = landmark;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Long getAlternatePhone() {
		return alternatePhone;
	}

	public void setAlternatePhone(Long alternatePhone) {
		this.alternatePhone = alternatePhone;
	}

	public String getAddressType() {
		return addressType;
	}

	public void setAddressType(String addressType) {
		this.addressType = addressType;
	}

	public Long getOrderFromCustomer() {
		return orderFromCustomer;
	}

	public void setOrderFromCustomer(Long orderFromCustomer) {
		this.orderFromCustomer = orderFromCustomer;
	}

	public Long getCustomersOrder() {
		return customersOrder;
	}

	public void setCustomersOrder(Long customersOrder) {
		this.customersOrder = customersOrder;
	}

	public String getOrderPlaceAt() {
		return orderPlaceAt;
	}

	public void setOrderPlaceAt(String orderPlaceAt) {
		this.orderPlaceAt = orderPlaceAt;
	}

	public String getOrderAcceptedAt() {
		return orderAcceptedAt;
	}

	public void setOrderAcceptedAt(String orderAcceptedAt) {
		this.orderAcceptedAt = orderAcceptedAt;
	}

	@Override
	public String toString() {
		return "OrderMaster [storeName=" + storeName + ", storePhone=" + storePhone + ", methodOfOrder=" + methodOfOrder
				+ ", dueDate=" + dueDate + ", dueTime=" + dueTime + ", orderNumber=" + orderNumber + ", notes=" + notes
				+ ", orderLine=" + orderLine + ", deliveryCharge=" + deliveryCharge + ", ServiceCharge=" + ServiceCharge
				+ ", totalDue=" + totalDue + ", orderStatus=" + orderStatus + ", customerId=" + customerId
				+ ", pincode=" + pincode + ", houseNoOrBuildingName=" + houseNoOrBuildingName
				+ ", roadNameAreaOrStreet=" + roadNameAreaOrStreet + ", city=" + city + ", state=" + state
				+ ", landmark=" + landmark + ", name=" + name + ", phone=" + phone + ", alternatePhone="
				+ alternatePhone + ", addressType=" + addressType + ", orderFromCustomer=" + orderFromCustomer
				+ ", customersOrder=" + customersOrder + ", orderPlaceAt=" + orderPlaceAt + ", orderAcceptedAt="
				+ orderAcceptedAt + "]";
	}

	
	
	
	
	
	
}
