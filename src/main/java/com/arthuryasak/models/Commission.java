package com.arthuryasak.models;

import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Table(name = "commissions")
public class Commission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "commission_id")
    private Integer commissionId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    @Column(name = "commission_percent")
    private Double commissionPercent;

    private Double result;

    public Integer getCommissionId() {
        return commissionId;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public Double getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Double commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public Double getResult() {
        return result;
    }

    public void setResult(Double result) {
        this.result = result;
    }
}
