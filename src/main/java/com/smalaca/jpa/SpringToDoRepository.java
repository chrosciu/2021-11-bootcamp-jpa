package com.smalaca.jpa;

import com.smalaca.jpa.domain.ToDo;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringToDoRepository extends CrudRepository<ToDo, UUID> {
}
