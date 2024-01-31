package eu.chrost;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Entity
public class Invoice {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private InvoiceStatus status;

    @OneToOne
    private Offer offer;

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    private Invoice(Buyer buyer, Seller seller, InvoiceStatus status) {
        this.buyer = buyer;
        this.seller = seller;
        this.status = status;
    }

    public static Invoice created(Buyer buyer, Seller seller) {
        return new Invoice(buyer, seller, InvoiceStatus.CREATED);
    }

    public void add(Offer offer) {
        this.offer = offer;
    }

    UUID getId() {
        return id;
    }
}
