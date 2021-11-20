package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.UUID;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Product {
    @Id
    @GeneratedValue
    private UUID id;
    @Column
    private String name;
    @Column
    private String description;
    @Transient
    private String shortDescription;

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
        updateShortDescription();
    }

    @PostLoad
    private void updateShortDescription() {
        if (description.length() <= 40) {
            shortDescription = description;
        } else {
            shortDescription = description.substring(0, 40) + "...";
        }
    }

    public void changeDescriptionTo(String description) {
        this.description = description;
        updateShortDescription();
    }

    UUID getId() {
        return id;
    }
}
