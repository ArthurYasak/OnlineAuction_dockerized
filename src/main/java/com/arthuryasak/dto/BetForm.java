package com.arthuryasak.dto;

import com.arthuryasak.models.Bet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BetForm {

    private Double betPrice;

    public Bet toBet() {
        Bet bet = Bet.builder()
                .betPrice(betPrice)
                .build();
        return bet;
    }
}