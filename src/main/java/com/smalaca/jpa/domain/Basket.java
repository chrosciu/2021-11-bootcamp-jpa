package com.smalaca.jpa.domain;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue
    private UUID id;

    @ElementCollection
    @CollectionTable(name = "PRODUCTS_IN_BASKET")
    @MapKeyColumn(name = "product_id", unique = true)
    @Column(name = "amount")
    private Map<UUID, Integer> products = new HashMap<>();

    public void addProducts(UUID id, int amount) {
        products.put(id, amount);
    }
}
