package com.arthuryasak.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Builder
@AllArgsConstructor
@Table(name = "lots")
public class Lot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lot_id")
    private Integer lotId;

    @ManyToOne(optional = false, cascade = {CascadeType.REFRESH}, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    private User userOwner;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lot", fetch = FetchType.EAGER, orphanRemoval = true)
    private LotProperty property;

    @Column(name = "sell_until")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime sellUntil;

    @Column(name = "min_price")
    private Double minPrice;

    @ManyToOne(cascade = {CascadeType.REFRESH, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinColumn(name = "last_customer_id")
    private User lastCustomer;

    @Column(name = "current_price")
    private Double currentPrice;

    @Column(name = "is_bought")
    private boolean isBought;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "lot", fetch = FetchType.EAGER, orphanRemoval = true)
    private Commission commission;

    public Lot() {
        this.property = new LotProperty(this);
    }

    public Integer getLotId() {
        return lotId;
    }

    public void setLotId(Integer id) {
        this.lotId = id;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public LotProperty getProperty() {
        return property;
    }
    public void setProperty(LotProperty property) {
        this.property = property;
        property.setLot(this);
    }

    public LocalDateTime getSellUntil() {
        return sellUntil;
    }

    public void setSellUntil(LocalDateTime soldUntil) {
        this.sellUntil = soldUntil;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public User getLastCustomer() {
        return lastCustomer;
    }

    public void setLastCustomer(User lastCustomer) {
        this.lastCustomer = lastCustomer;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public boolean isBought() {
        return isBought;
    }

    public void setBought(boolean bought) {
        isBought = bought;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(Commission commission) {
        this.commission = commission;
        commission.setLot(this);
    }

    @Override
    public String toString() {
        return "Lot{" +
                "lotId=" + lotId +
                ", userOwnerId=" + userOwner.getUserId() +
                ", property: id=" + (property == null ? "not" : property.getPropertyId()) +
                ", property: name=" + (property == null ? "not" : property.getName()) +
                ", property: type=" + (property == null ? "not" : property.getType()) +
                ", property: weight=" + (property == null ? "not" : property.getWeight()) +
                ", property: size=" + (property == null ? "not" : property.getSize()) +
                ", property: description=" + (property == null ? "not" : property.getDescription()) +
                ", sellUntil=" + sellUntil +
                ", minPrice=" + minPrice +
                ", lastCustomerId=" + (lastCustomer == null ? "not" : lastCustomer.getUserId()) +
                ", currentPrice=" + currentPrice +
                ", isBought=" + isBought +
                ", commission percent=" + (commission == null ? "not" : commission.getCommissionPercent()) +
        "}\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lot lot = (Lot) o;
        return Objects.equals(getLotId(), lot.getLotId()) && Objects.equals(getUserOwner(), lot.getUserOwner()) && Objects.equals(getProperty(), lot.getProperty()) && Objects.equals(getSellUntil(), lot.getSellUntil()) && Objects.equals(getMinPrice(), lot.getMinPrice()) && Objects.equals(getLastCustomer(), lot.getLastCustomer()) && Objects.equals(getCurrentPrice(), lot.getCurrentPrice());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLotId(), getSellUntil(), getMinPrice());
    }
}