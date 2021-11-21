package com.smalaca.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class User extends Person {
    @Column
    private long currentSessionLength;

    @Column
    private long lastSessionLength;
}
