package com.smalaca.jpa.domain;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.time.LocalDate;

@Entity
@DiscriminatorValue("EMPLOYEE")
public class Employee extends Person {
    @Column
    private LocalDate workStartDate;
}
