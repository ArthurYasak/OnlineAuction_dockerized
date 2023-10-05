package com.arthuryasak.models;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import javax.imageio.ImageIO;
import javax.persistence.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;
import java.util.Objects;
import java.util.Base64.Encoder;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
    @Log4j2
    @Table(name = "lots_properties")
    public class LotProperty implements Serializable {
        @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "property_id")
    private Integer propertyId;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "lot_id")
    private Lot lot;

    private String name;

    private String type;

    private Integer weight;

    private Integer size;

    private String description;

    private byte[] photo;

    public LotProperty(Lot lot) {
        this.lot = lot;
    }

    public Integer getPropertyId() {
        return propertyId;
    }

    public Lot getLot() {
        return lot;
    }

    public void setLot(Lot lot) {
        this.lot = lot;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getBytePhoto() {
        return this.photo;
    }

    public String getBase64Photo() {
        if (photo != null) {
            Encoder base64Encoder = Base64.getEncoder();
            String image = base64Encoder.encodeToString(photo);
            return image;
        }
        return null;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LotProperty that = (LotProperty) o;
        return Objects.equals(getPropertyId(), that.getPropertyId()) && Objects.equals(getLot(), that.getLot())
                && Objects.equals(getWeight(), that.getWeight()) && Objects.equals(getSize(), that.getSize())
                && Objects.equals(getDescription(), that.getDescription());
//                && Objects.equals(getImagePhoto(), that.getImagePhoto())
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(getPropertyId(), getWeight(), getSize(), getDescription()/*, getImagePhoto()*/);
        result = 31 * result;
        return result;
    }

    @Override
    public String toString() {
        return "LotProperty{" +
                "propertyId=" + propertyId +
                ", lot=" + lot +
                ", weight=" + weight +
                ", size=" + size +
                ", description='" + description + '\'' +
                ", photo=" + photo +
                '}';
    }
}
