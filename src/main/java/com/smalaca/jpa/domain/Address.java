package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Address {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String postalCode;

    @Column
    private String country;

    public Address(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }
}
