package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Offer {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String offerNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OfferItem> offerItems = new ArrayList<>();

    public Offer(String offerNumber) {
        this.offerNumber = offerNumber;
    }

    UUID getId() {
        return id;
    }

    public void add(OfferItem offerItem) {
        offerItems.add(offerItem);
    }
}
