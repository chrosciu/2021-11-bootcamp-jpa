package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.InvoiceStatus;
import lombok.Value;

@Value
public class CountWithStatus {
    long count;
    InvoiceStatus status;
}
