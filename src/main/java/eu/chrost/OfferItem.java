package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OfferItem {
    @Id
    @GeneratedValue
    private UUID id;

    @Column
    private UUID productId;

    @Column
    private int amount;

    public OfferItem(UUID productId, int amount) {
        this.productId = productId;
        this.amount = amount;
    }

    UUID getId() {
        return id;
    }
}
