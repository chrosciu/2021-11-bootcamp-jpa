package com.smalaca.jpa.domain;

import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PostLoad;
import javax.persistence.Transient;
import java.util.UUID;

@ToString
@Entity(name = "ToDo")
public class ToDo {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String subject;

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
