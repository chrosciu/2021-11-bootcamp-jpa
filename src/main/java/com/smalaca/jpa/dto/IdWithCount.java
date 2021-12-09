package com.smalaca.jpa.dto;

import lombok.Value;

import java.util.UUID;

@Value
public class IdWithCount {
    UUID id;
    long count;
 }
