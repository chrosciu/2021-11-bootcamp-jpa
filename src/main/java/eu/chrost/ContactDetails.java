package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embeddable;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Embeddable
public class ContactDetails {
    private String login;
    private String phone;
    private String mail;

    public ContactDetails(String login, String phone, String mail) {
        this.login = login;
        this.phone = phone;
        this.mail = mail;
    }
}
