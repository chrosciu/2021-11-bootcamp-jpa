package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
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
public class Seller {
    @Id
    @GeneratedValue
    private UUID id;

    @Embedded
    @AttributeOverrides(value = {
            @AttributeOverride(name = "phone", column = @Column(name = "seller_phone")),
            @AttributeOverride(name = "mail", column = @Column(name = "seller_mail")),
    })
    private ContactDetails contactDetails;

    @OneToMany(mappedBy = "seller")
    private Set<Invoice> invoices = new HashSet<>();

    public Seller(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
    }

    UUID getId() {
        return id;
    }
}
