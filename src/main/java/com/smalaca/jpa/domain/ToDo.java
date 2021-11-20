package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity(name = "ToDo")
public class ToDo {
    @Id
    private UUID id;

    @Column
    private String subject;

    public ToDo(UUID id, String subject) {
        this.id = id;
        this.subject = subject;
    }
}
