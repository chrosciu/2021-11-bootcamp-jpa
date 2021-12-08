package com.smalaca.jpa.domain;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@NoArgsConstructor
@ToString
@Embeddable
@EqualsAndHashCode
public class Rating {
    private String login;
    private int value;
    private String explanation;

    public Rating(String login, int value, String explanation) {
        this.login = login;
        this.value = value;
        this.explanation = explanation;
    }

    public Rating(String login, int value) {
        this.login = login;
        this.value = value;
    }
}
