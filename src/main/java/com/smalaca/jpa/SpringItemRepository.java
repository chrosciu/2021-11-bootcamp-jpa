package com.smalaca.jpa;

import com.smalaca.jpa.domain.Item;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringItemRepository extends CrudRepository<Item, UUID> {
}
