package com.smalaca.jpa.domain;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.UUID;

@ToString
@Entity(name = "ToDo")
@Table(name = "TODOS")
public class ToDo {
    @Id
    @GeneratedValue
    @Column(name = "TODO_ID")
    private UUID id;

    @Column(name = "TODO_SUBJECT", unique = true, nullable = false)
    private String subject;

    @Column(name = "TODO_DETAILS", columnDefinition = "CLOB")
    private String details;

    @Enumerated(EnumType.ORDINAL)
    private ToDoStatus status;

    @Transient
    private String firstLetterOfSubject;

    public ToDo(String subject) {
        this.subject = subject;
        this.firstLetterOfSubject = calculateFirstLetterOfSubject();
    }

    private ToDo() {}

    @PostLoad
    private void fillFirstLetterOfSubject() {
        this.firstLetterOfSubject = calculateFirstLetterOfSubject();
    }

    private String calculateFirstLetterOfSubject() {
        return this.subject.substring(0,1);
    }

    public void changeSubjectTo(String subject) {
        this.subject = subject;
    }

    UUID getId() {
        return id;
    }

}
