package com.smalaca.jpa.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class IdAndCount {
    UUID id;
    long count;
}
