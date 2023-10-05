package com.arthuryasak.dto;

import com.arthuryasak.models.Lot;
import com.arthuryasak.models.LotProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.validation.constraints.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Log4j2
public class LotForm {
    @NotEmpty(message = "Name should not be empty")
    @Size(max = 150, message = "Name length can't be more than 150 symbols")
    private String name;
    @NotEmpty(message = "Type should not be empty")
    @Size(max = 150, message = "Type length can't be more than 150 symbols")
    private String type;
    @NotNull(message = "Weight should not be empty")
    @Min(value = 0, message = "Can't be less than 0")
    private Integer weight;
    @NotNull(message = "Size should not be empty")
    @Min(value = 0, message = "Can't be less than 0")
    private Integer size;
    private String description;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @NotNull(message = "Date should not be empty")
    @Future(message = "Date must be in the future")
    private LocalDateTime sellUntil;

    @NotNull(message = "Min price should not be empty")
    @Min(value = 0, message = "Can't be less than 0")
    private Double minPrice;

    private MultipartFile multipartFile;

    public LotForm(Lot lot) {
        this.name = lot.getProperty().getName();
        this.type = lot.getProperty().getType();
        this.weight = lot.getProperty().getWeight();
        this.size = lot.getProperty().getSize();
        this.description = lot.getProperty().getDescription();
        this.sellUntil = lot.getSellUntil();
        this.minPrice = lot.getMinPrice();
    }

    public Lot toLot() {
        LotProperty lotProperty = LotProperty.builder()
                .name(name)
                .type(type)
                .weight(weight)
                .size(size)
                .description(description)
//                    .photo(photo)
                .build();
        Lot lot = Lot.builder()
                .sellUntil(sellUntil)
                .minPrice(minPrice)
                .currentPrice(minPrice)
                .build();
        lot.setProperty(lotProperty);
        return lot;

    }
}
