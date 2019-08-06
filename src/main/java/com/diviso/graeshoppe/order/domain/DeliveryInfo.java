package com.diviso.graeshoppe.order.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * A DeliveryInfo.
 */
@Entity
@Table(name = "delivery_info")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName = "orderdeliveryinfo",type="orderdeliveryinfo")
public class DeliveryInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "delivery_type")
    private String deliveryType;

    @Column(name = "expected_delivery")
    private Instant expectedDelivery;

    @Column(name = "delivery_charge")
    private Double deliveryCharge;

    @ManyToOne
    @JsonIgnoreProperties("deliveryInfos")
    private Address deliveryAddress;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDeliveryType() {
        return deliveryType;
    }

    public DeliveryInfo deliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
        return this;
    }

    public void setDeliveryType(String deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Instant getExpectedDelivery() {
        return expectedDelivery;
    }

    public DeliveryInfo expectedDelivery(Instant expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
        return this;
    }

    public void setExpectedDelivery(Instant expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }

    public Double getDeliveryCharge() {
        return deliveryCharge;
    }

    public DeliveryInfo deliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
        return this;
    }

    public void setDeliveryCharge(Double deliveryCharge) {
        this.deliveryCharge = deliveryCharge;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public DeliveryInfo deliveryAddress(Address address) {
        this.deliveryAddress = address;
        return this;
    }

    public void setDeliveryAddress(Address address) {
        this.deliveryAddress = address;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DeliveryInfo deliveryInfo = (DeliveryInfo) o;
        if (deliveryInfo.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), deliveryInfo.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DeliveryInfo{" +
            "id=" + getId() +
            ", deliveryType='" + getDeliveryType() + "'" +
            ", expectedDelivery='" + getExpectedDelivery() + "'" +
            ", deliveryCharge=" + getDeliveryCharge() +
            "}";
    }
}
