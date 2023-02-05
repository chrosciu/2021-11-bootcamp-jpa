package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.InvoiceStatus;
import lombok.Value;

import java.util.UUID;

@Value
public class IdAndStatus {
    UUID id;
    InvoiceStatus status;

    public IdAndStatus(UUID id, InvoiceStatus status) {
        this.id = id;
        this.status = status;
    }

    public IdAndStatus(String uuidAsString, String statusAsString) {
        this(UUID.fromString(uuidAsString), InvoiceStatus.valueOf(statusAsString));
    }
}
