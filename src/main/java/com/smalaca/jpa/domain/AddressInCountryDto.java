package com.smalaca.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class AddressInCountryDto {
    private final String street;
    private final String city;
    private final String postalCode;
}
