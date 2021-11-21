package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Watcher {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String login;

    @ManyToMany(mappedBy = "watchers")
    private Set<ToDo> toDos = new HashSet<>();

    public Watcher(String login) {
        this.login = login;
    }

    @Override
    public String toString() {
        return "Watcher{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", toDos=" + toDos.size() +
                '}';
    }

    public void add(ToDo toDo) {
        justAdd(toDo);
        toDo.justAdd(this);
    }

    void justAdd(ToDo toDo) {
        toDos.add(toDo);
    }

    UUID getId() {
        return id;
    }
}