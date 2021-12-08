package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.InvoiceStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class IdWithStatus {
    UUID id;
    InvoiceStatus status;
}
