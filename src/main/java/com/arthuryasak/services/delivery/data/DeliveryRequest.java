package com.arthuryasak.services.delivery.data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DeliveryRequest {

    private String receiverName;

    private String receiverSurname;

    private Integer receiverAge;

    private String receiverTelephone;

    private String receiverEmail;

    private String receiverAddress;

    private byte[] productPhoto;
}
