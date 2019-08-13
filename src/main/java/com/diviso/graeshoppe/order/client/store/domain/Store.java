package com.diviso.graeshoppe.order.client.store.domain;




import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.GeoPointField;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Store.
 */
@Document(indexName = "store")
public class Store implements Serializable {

    private static final long serialVersionUID = 1L;
    
   

	private Long id;

   
    private String regNo;

  
    private String name;

    @Lob
    private byte[] image;

 
    private String imageContentType;

    private Double totalRating;


    @GeoPointField
    private String location;


    private String locationName;

    private Long contactNo;

    private Instant openingTime;

    private String email;

    private Instant closingTime;


    private String info;

    private Double minAmount;

   
    private Instant maxDeliveryTime;

   
    private Propreitor propreitor;

   
    public StoreSettings getStoreSettings() {
		return storeSettings;
	}

	public void setStoreSettings(StoreSettings storeSettings) {
		this.storeSettings = storeSettings;
	}

	private StoreAddress storeAddress;
    private StoreSettings storeSettings;
    private Set<StoreType> storeTypes = new HashSet<>();
   
    private Set<Review> reviews = new HashSet<>();
   
    private Set<UserRating> userRatings = new HashSet<>();
   
    private Set<Banner> banners = new HashSet<>();
   
    private Set<DeliveryInfo> deliveryInfos = new HashSet<>();
    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public Store regNo(String regNo) {
        this.regNo = regNo;
        return this;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public Store name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getImage() {
        return image;
    }

    public Store image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return imageContentType;
    }

    public Store imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Double getTotalRating() {
        return totalRating;
    }

    public Store totalRating(Double totalRating) {
        this.totalRating = totalRating;
        return this;
    }

    public void setTotalRating(Double totalRating) {
        this.totalRating = totalRating;
    }

    public String getLocation() {
        return location;
    }

    public Store location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocationName() {
        return locationName;
    }

    public Store locationName(String locationName) {
        this.locationName = locationName;
        return this;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public Store contactNo(Long contactNo) {
        this.contactNo = contactNo;
        return this;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public Instant getOpeningTime() {
        return openingTime;
    }

    public Store openingTime(Instant openingTime) {
        this.openingTime = openingTime;
        return this;
    }

    public void setOpeningTime(Instant openingTime) {
        this.openingTime = openingTime;
    }

    public String getEmail() {
        return email;
    }

    public Store email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getClosingTime() {
        return closingTime;
    }

    public Store closingTime(Instant closingTime) {
        this.closingTime = closingTime;
        return this;
    }

    public void setClosingTime(Instant closingTime) {
        this.closingTime = closingTime;
    }

    public String getInfo() {
        return info;
    }

    public Store info(String info) {
        this.info = info;
        return this;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Double getMinAmount() {
        return minAmount;
    }

    public Store minAmount(Double minAmount) {
        this.minAmount = minAmount;
        return this;
    }

    public void setMinAmount(Double minAmount) {
        this.minAmount = minAmount;
    }

    public Instant getMaxDeliveryTime() {
        return maxDeliveryTime;
    }

    public Store maxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
        return this;
    }

    public void setMaxDeliveryTime(Instant maxDeliveryTime) {
        this.maxDeliveryTime = maxDeliveryTime;
    }

    public Propreitor getPropreitor() {
        return propreitor;
    }

    public Store propreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
        return this;
    }

    public void setPropreitor(Propreitor propreitor) {
        this.propreitor = propreitor;
    }

    public StoreAddress getStoreAddress() {
        return storeAddress;
    }

    public Store storeAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
        return this;
    }

    public void setStoreAddress(StoreAddress storeAddress) {
        this.storeAddress = storeAddress;
    }

    public Set<StoreType> getStoreTypes() {
        return storeTypes;
    }

    public Store storeTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
        return this;
    }

    public Store addStoreType(StoreType storeType) {
        this.storeTypes.add(storeType);
        storeType.setStore(this);
        return this;
    }

    public Store removeStoreType(StoreType storeType) {
        this.storeTypes.remove(storeType);
        storeType.setStore(null);
        return this;
    }

    public void setStoreTypes(Set<StoreType> storeTypes) {
        this.storeTypes = storeTypes;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public Store reviews(Set<Review> reviews) {
        this.reviews = reviews;
        return this;
    }

    public Store addReview(Review review) {
        this.reviews.add(review);
        review.setStore(this);
        return this;
    }

    public Store removeReview(Review review) {
        this.reviews.remove(review);
        review.setStore(null);
        return this;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<UserRating> getUserRatings() {
        return userRatings;
    }

    public Store userRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
        return this;
    }

    public Store addUserRating(UserRating userRating) {
        this.userRatings.add(userRating);
        userRating.setStore(this);
        return this;
    }

    public Store removeUserRating(UserRating userRating) {
        this.userRatings.remove(userRating);
        userRating.setStore(null);
        return this;
    }

    public void setUserRatings(Set<UserRating> userRatings) {
        this.userRatings = userRatings;
    }

    public Set<Banner> getBanners() {
        return banners;
    }

    public Store banners(Set<Banner> banners) {
        this.banners = banners;
        return this;
    }

    public Store addBanner(Banner banner) {
        this.banners.add(banner);
        banner.setStore(this);
        return this;
    }

    public Store removeBanner(Banner banner) {
        this.banners.remove(banner);
        banner.setStore(null);
        return this;
    }

    public void setBanners(Set<Banner> banners) {
        this.banners = banners;
    }

    public Set<DeliveryInfo> getDeliveryInfos() {
        return deliveryInfos;
    }

    public Store deliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
        return this;
    }

    public Store addDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.add(deliveryInfo);
        deliveryInfo.setStore(this);
        return this;
    }

    public Store removeDeliveryInfo(DeliveryInfo deliveryInfo) {
        this.deliveryInfos.remove(deliveryInfo);
        deliveryInfo.setStore(null);
        return this;
    }

    public void setDeliveryInfos(Set<DeliveryInfo> deliveryInfos) {
        this.deliveryInfos = deliveryInfos;
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
        Store store = (Store) o;
        if (store.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), store.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Store{" +
            "id=" + getId() +
            ", regNo='" + getRegNo() + "'" +
            ", name='" + getName() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", totalRating=" + getTotalRating() +
            ", location='" + getLocation() + "'" +
            ", locationName='" + getLocationName() + "'" +
            ", contactNo=" + getContactNo() +
            ", openingTime='" + getOpeningTime() + "'" +
            ", email='" + getEmail() + "'" +
            ", closingTime='" + getClosingTime() + "'" +
            ", info='" + getInfo() + "'" +
            ", minAmount=" + getMinAmount() +
            ", maxDeliveryTime='" + getMaxDeliveryTime() + "'" +
            "}";
    }
}
