package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@Entity
@ToString(exclude = "invoice")
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class InvoiceItem {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToOne
    private Product product;

    @Column
    private int amount;

    @ManyToOne
    @JoinColumn(name = "invoice_id", nullable = false)
    private Invoice invoice;

    public InvoiceItem(Invoice invoice, Product product, int amount) {
        this.invoice = invoice;
        this.product = product;
        this.amount = amount;
    }

    UUID getId() {
        return id;
    }
}
