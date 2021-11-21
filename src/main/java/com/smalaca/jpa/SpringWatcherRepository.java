package com.smalaca.jpa;

import com.smalaca.jpa.domain.Watcher;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SpringWatcherRepository extends CrudRepository<Watcher, UUID> {
}
