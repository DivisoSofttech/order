package com.diviso.graeshoppe.order.client.store.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A UserRating.
 */

@Document(indexName = "userrating")
public class UserRating implements Serializable {

    private static final long serialVersionUID = 1L;
  
    private Long id;


    private String userName;


    private Double rating;

    private ZonedDateTime ratedOn;

    private Store store;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public UserRating userName(String userName) {
        this.userName = userName;
        return this;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Double getRating() {
        return rating;
    }

    public UserRating rating(Double rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public ZonedDateTime getRatedOn() {
        return ratedOn;
    }

    public UserRating ratedOn(ZonedDateTime ratedOn) {
        this.ratedOn = ratedOn;
        return this;
    }

    public void setRatedOn(ZonedDateTime ratedOn) {
        this.ratedOn = ratedOn;
    }

    public Store getStore() {
        return store;
    }

    public UserRating store(Store store) {
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
        UserRating userRating = (UserRating) o;
        if (userRating.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), userRating.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UserRating{" +
            "id=" + getId() +
            ", userName='" + getUserName() + "'" +
            ", rating=" + getRating() +
            ", ratedOn='" + getRatedOn() + "'" +
            "}";
    }
}
