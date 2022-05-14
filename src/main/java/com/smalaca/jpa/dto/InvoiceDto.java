package com.smalaca.jpa.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class InvoiceDto {
    UUID id; //id faktury
    String offerNumber; //numer oferty
    long offerItemAmount; //liczba elementów oferty
}
