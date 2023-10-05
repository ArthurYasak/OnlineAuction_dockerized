package com.arthuryasak.models;


import lombok.AllArgsConstructor;
import lombok.Builder;

import javax.persistence.*;

@Entity
@Table(name = "bets")
@Builder
@AllArgsConstructor
public class Bet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bet_id")
    private Integer betId;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_owner_id")
    private User userOwner;

    @OneToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "lot_id", unique = false)
    private Lot lot;

    @Column(name = "bet_price")
    private Double betPrice;

    public Bet() {
        this.betPrice = 0.0;
    }

    public Bet(Double betPrice) {
        this.betPrice = betPrice;
    }

    public Integer getBetId() {
        return betId;
    }

    public User getUserOwner() {
        return userOwner;
    }

    public void setUserOwner(User userOwner) {
        this.userOwner = userOwner;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Double getBetPrice() {
        return betPrice;
    }

    public void setBetPrice(Double betPrice) {
        this.betPrice = betPrice;
    }

    @Override
    public String toString() {
        return "Bet{" +
                "betId=" + betId +
                ", userOwnerId=" + (userOwner == null ? "null user" : userOwner.getUserId()) +
                ", lotId=" + lot.getLotId() +
                ", betPrice=" + betPrice +
                '}';
    }
}
