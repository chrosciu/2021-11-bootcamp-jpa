package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Buyer {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    private ContactDetails contactDetails;

    public Buyer(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    UUID getId() {
        return id;
    }
}
