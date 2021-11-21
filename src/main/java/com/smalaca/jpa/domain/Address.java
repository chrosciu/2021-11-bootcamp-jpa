package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import java.util.UUID;

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

    @ManyToOne
    @JoinTable(
            name = "all_addresses_of_author",
            joinColumns = {@JoinColumn(name = "id_of_address")},
            inverseJoinColumns = {@JoinColumn(name = "id_of_author")}
    )
    private Author author;

    public Address(String street, String city, String postalCode, String country) {
        this.street = street;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public UUID getId() {
        return id;
    }

    void set(Author author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", street='" + street + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                ", country='" + country + '\'' +
                ", author=" + (author == null ? "NO AUTHOR" : author.getId()) +
                '}';
    }
}
