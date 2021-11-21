package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Watcher {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String login;

    public Watcher(String login) {
        this.login = login;
    }
}