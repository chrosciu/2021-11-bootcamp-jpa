package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.InvoiceStatus;
import lombok.Value;

import java.nio.ByteBuffer;
import java.util.UUID;

@Value
public class IdAndStatus {
    UUID id;
    InvoiceStatus status;

    public IdAndStatus(UUID id, InvoiceStatus status) {
        this.id = id;
        this.status = status;
    }

    public IdAndStatus(byte[] bytes, String statusAsString) {
        this(idFromBytes(bytes), InvoiceStatus.valueOf(statusAsString));
    }

    private static UUID idFromBytes(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
