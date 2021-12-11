package com.smalaca.jpa.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class InvoiceDto {
    UUID id;
    String offerNumber;
    long offerItemAmount;
}
