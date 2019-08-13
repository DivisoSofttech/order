package com.diviso.graeshoppe.order.client.store.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Type.
 */

@Document(indexName = "type")
public class Type implements Serializable {

    
 
    private Long id;

   
    private String name;

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

    public Type name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
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
        Type type = (Type) o;
        if (type.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), type.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Type{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
