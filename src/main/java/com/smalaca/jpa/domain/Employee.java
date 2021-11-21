package com.smalaca.jpa.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
public class Employee extends Person {
    @Column
    private LocalDate workStartDate;
}
