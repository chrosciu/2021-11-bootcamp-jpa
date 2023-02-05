package com.smalaca.jpa.dto;

import com.smalaca.jpa.domain.Buyer;
import lombok.Value;

@Value
public class BuyerAndCount {
    Buyer buyer;
    long count;
}
