package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceItem {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Product product;

    @Column
    private int amount;

    public InvoiceItem(Product product, int amount) {
        this.product = product;
        this.amount = amount;
    }

    UUID getId() {
        return id;
    }
}
