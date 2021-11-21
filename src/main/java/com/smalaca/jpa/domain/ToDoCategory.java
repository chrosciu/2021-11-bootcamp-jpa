package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter(AccessLevel.PACKAGE)
public class ToDoCategory {
    private final String shortName;
    private final String fullName;

    public ToDoCategory(String shortName, String fullName) {
        this.shortName = shortName;
        this.fullName = fullName;
    }
}
