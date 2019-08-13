package com.diviso.graeshoppe.order.client.store.domain;


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

@Document(indexName = "deliveryinfo")
public class DeliveryInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private Long id;

    private Instant startingTime;

    private Instant endTime;


    private Store store;

    private Type type;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStartingTime() {
        return startingTime;
    }

    public DeliveryInfo startingTime(Instant startingTime) {
        this.startingTime = startingTime;
        return this;
    }

    public void setStartingTime(Instant startingTime) {
        this.startingTime = startingTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public DeliveryInfo endTime(Instant endTime) {
        this.endTime = endTime;
        return this;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public Store getStore() {
        return store;
    }

    public DeliveryInfo store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Type getType() {
        return type;
    }

    public DeliveryInfo type(Type type) {
        this.type = type;
        return this;
    }

    public void setType(Type type) {
        this.type = type;
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
            ", startingTime='" + getStartingTime() + "'" +
            ", endTime='" + getEndTime() + "'" +
            "}";
    }
}
