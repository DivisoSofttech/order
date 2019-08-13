package com.diviso.graeshoppe.order.client.store.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.util.Objects;

/**
 * A StoreType.
 */

@Document(indexName = "storetype")
public class StoreType implements Serializable {

    private static final long serialVersionUID = 1L;
    
   
    private Long id;

    private String name;

    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public StoreType name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Store getStore() {
        return store;
    }

    public StoreType store(Store store) {
        this.store = store;
        return this;
    }

    public void setStore(Store store) {
        this.store = store;
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
        StoreType storeType = (StoreType) o;
        if (storeType.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), storeType.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "StoreType{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
