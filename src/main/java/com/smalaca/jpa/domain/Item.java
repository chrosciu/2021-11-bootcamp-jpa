package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "shortDescription", column = @Column(name = "item_short_desc", nullable = false)),
            @AttributeOverride(name = "fullDescription", column = @Column(name = "item_full_desc"))
    })
    private Description description;

    public Item(Description description) {
        this.description = description;
    }

    UUID getId() {
        return id;
    }
}
