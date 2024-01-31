package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@ToString(exclude = "invoices")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Buyer {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "buyer")
    private Set<Invoice> invoices = new HashSet<>();

    public Buyer(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    UUID getId() {
        return id;
    }
}
