package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
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

    @ElementCollection
    @CollectionTable(name = "CATEGORIES")
    @Column(name = "CATEGORY")
    private Set<String> categories = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "RATINGS")
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "login", column = @Column(nullable = false)),
            @AttributeOverride(name = "value", column = @Column(name = "rating_value", nullable = false)),
            @AttributeOverride(name = "explanation", column = @Column(name = "rating_expl")),
    })
    private Set<Rating> ratings = new HashSet<>();

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

    public void addCategory(String category) {
        categories.add(category);
    }

    public void add(Rating rating) {
        ratings.add(rating);
    }

    UUID getId() {
        return id;
    }
}
