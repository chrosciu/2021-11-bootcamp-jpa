package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.InvoiceStatus;
import lombok.Value;

import java.nio.ByteBuffer;
import java.util.UUID;

@Value
public class IdWithStatus {
    UUID id;
    InvoiceStatus status;

    public IdWithStatus(UUID id, InvoiceStatus status) {
        this.id = id;
        this.status = status;
    }

    public IdWithStatus(byte[] bytes, String statusAsString) {
        this(idFromBytes(bytes), InvoiceStatus.valueOf(statusAsString));
    }

    private static UUID idFromBytes(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
