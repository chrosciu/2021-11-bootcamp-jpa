package com.smalaca.jpa.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@ToString
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class BasketIdentifier {
    private final String login;
    private final int visits;
    private final LocalDate creationDate;
}
