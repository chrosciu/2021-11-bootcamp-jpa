package com.smalaca.jpa.dto;

import lombok.Value;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.UUID;

@Value
public class InvoiceDto {
    UUID id;
    String offerNumber;
    long offerItemAmount;

    public InvoiceDto(UUID id, String offerNumber, long offerItemAmount) {
        this.id = id;
        this.offerNumber = offerNumber;
        this.offerItemAmount = offerItemAmount;
    }

    public InvoiceDto(byte[] bytes, String offerNumber, BigInteger offerItemAmount) {
        this.id = idFromBytes(bytes);
        this.offerNumber = offerNumber;
        this.offerItemAmount = offerItemAmount.intValue();
    }

    private static UUID idFromBytes(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
