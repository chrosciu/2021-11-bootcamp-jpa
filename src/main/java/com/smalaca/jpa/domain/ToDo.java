package com.smalaca.jpa.domain;

import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashSet;
import java.util.Set;
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

    @Embedded
    private Description description;

    @Enumerated(EnumType.ORDINAL)
    private ToDoStatus status;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "comments", joinColumns = {
            @JoinColumn(name = "todo_id")
    })
    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "author", column = @Column(name = "auth")),
            @AttributeOverride(name = "comment", column = @Column(name = "cmnt"))
    })
    private Set<Comment> comments = new HashSet<>();

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

    public void add(Description description) {
        this.description = description;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}
