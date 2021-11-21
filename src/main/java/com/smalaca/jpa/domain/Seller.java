package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Seller {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "phone", column = @Column(name = "seller_phone")),
            @AttributeOverride(name = "mail", column = @Column(name = "seller_mail")),
    })
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "seller")
    private Set<Invoice> invoices = new HashSet<>();

    @ManyToMany(cascade = CascadeType.ALL)
    private Set<ProductDefinition> productDefinitions = new HashSet<>();

    public Seller(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    public UUID getId() {
        return id;
    }

    public void add(ProductDefinition productDefinition) {
        productDefinitions.add(productDefinition);
    }
}
