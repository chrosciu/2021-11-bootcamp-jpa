package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@ToString
@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private InvoiceStatus status;

    @OneToOne
    private Offer offer;

    public static Invoice created() {
        Invoice invoice = new Invoice();
        invoice.status = InvoiceStatus.CREATED;
        return invoice;
    }

    public void add(Offer offer) {
        this.offer = offer;
    }

    UUID getId() {
        return id;
    }
}
