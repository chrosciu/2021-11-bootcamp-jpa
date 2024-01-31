package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@ToString
@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Offer {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private String offerNumber;

    public Offer(String offerNumber) {
        this.offerNumber = offerNumber;
    }

    UUID getId() {
        return id;
    }
}
