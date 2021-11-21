package com.smalaca.jpa;

import com.smalaca.jpa.domain.Author;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringAuthorRepository extends CrudRepository<Author, UUID> {
}
