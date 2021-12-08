package com.smalaca.jpa.dto;

import lombok.Value;

@Value
public class SellerAndBuyerId {
    byte[] sellerId;
    byte[] buyerId;
}
