package com.smalaca.jpa.domain;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Author {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @OneToOne(mappedBy = "author")
    private ToDo toDo;

    @OneToMany(cascade = {CascadeType.PERSIST})
    private Set<Address> addresses = new HashSet<>();

    public Author(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Author{" +
                "id=" + id +
                ", addresses =" + addresses +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", toDo=" + (toDo != null ? toDo.getId() : "NO TODO") +
                '}';
    }

    public void add(Address address) {
        addresses.add(address);
    }
}
