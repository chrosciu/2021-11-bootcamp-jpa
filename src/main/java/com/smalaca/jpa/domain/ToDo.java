package com.smalaca.jpa.domain;

import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.CascadeType;
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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

    @ElementCollection
    @CollectionTable(name = "tags")
    @MapKeyColumn(name = "name", unique = true)
    @Column(name = "description")
    private Map<String, String> tags = new HashMap<>();

    private ToDoCategory category;

    @OneToOne
    @JoinColumn(name = "auth_id")
    private Author author;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "watchers_of_todos",
            joinColumns = {@JoinColumn(name = "todo_id")},
            inverseJoinColumns = {@JoinColumn(name = "watcher_id")}
    )
    private Set<Watcher> watchers = new HashSet<>();

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

    public void addTag(String name, String description) {
        tags.put(name, description);
    }

    public void setCategory(ToDoCategory category) {
        this.category = category;
    }

    public void set(Author author) {
        this.author = author;
    }

    public void add(Watcher watcher) {
        justAdd(watcher);
        watcher.justAdd(this);
    }

    void justAdd(Watcher watcher) {
        watchers.add(watcher);
    }
}
