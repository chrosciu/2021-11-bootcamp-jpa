package com.smalaca.jpa.domain;

public interface AddressInCountryView {
    String getStreet();
    String getCity();
    String getPostalCode();

    AuthorView getAuthor();

    interface AuthorView {
        String getFirstName();
        String getLastName();
    }
}
