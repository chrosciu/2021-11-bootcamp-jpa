package com.smalaca.jpa;

import com.smalaca.jpa.domain.ToDo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringToDoRepository extends CrudRepository<ToDo, UUID> {
    @Query("SELECT t FROM ToDo t WHERE t.subject =:sub")
    List<ToDo> findAllWithSubject(@Param("sub") String subject);
}
