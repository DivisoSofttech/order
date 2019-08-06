package com.diviso.graeshoppe.order.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the UniqueOrderID entity.
 */
public class UniqueOrderIDDTO implements Serializable {

    private Long id;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        UniqueOrderIDDTO uniqueOrderIDDTO = (UniqueOrderIDDTO) o;
        if (uniqueOrderIDDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), uniqueOrderIDDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "UniqueOrderIDDTO{" +
            "id=" + getId() +
            "}";
    }
}
