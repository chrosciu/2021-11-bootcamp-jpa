package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
    private Description description;

    public Item(Description description) {
        this.description = description;
    }

    UUID getId() {
        return id;
    }
}
