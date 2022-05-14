package com.smalaca.jpa.domain;

import com.smalaca.jpa.dto.InvoiceDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SqlResultSetMapping;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Entity
@NamedQuery(name = "Invoice.findBySellerLogin", query = "from Invoice i where i.seller.contactDetails.login = :login")
@NamedNativeQuery(name = "Invoice.findBySellerLoginNative",
        query = "SELECT * from invoice i JOIN seller s ON i.seller_id = s.id WHERE s.login = :login",
        resultClass = Invoice.class)
@SqlResultSetMapping(
        name = "Invoice.idAndStatus",
        classes = {
                @ConstructorResult(
                        targetClass = com.smalaca.jpa.dto.IdAndStatus.class,
                        columns = {
                                @ColumnResult(name = "id"),
                                @ColumnResult(name = "status")
                        }
                )
        }
)
@SqlResultSetMapping(
        name = "invoiceDto",
        classes = {
                @ConstructorResult(
                        targetClass = com.smalaca.jpa.dto.InvoiceDto.class,
                        columns = {
                                @ColumnResult(name = "id"),
                                @ColumnResult(name = "offerNumber"),
                                @ColumnResult(name = "offerItemAmount")
                        }
                )
        }
)
@NamedNativeQuery(name = "Invoice.findByOfferNumberOrStatus",
        query = "SELECT " +
                "i.id, o.OFFERNUMBER, count(oi.ID) as offerItemAmount " +
                "from invoice i " +
                "JOIN offer o ON i.offer_id = o.id " +
                "LEFT JOIN OFFER_OFFERITEM o_oi ON o.id = o_oi.OFFER_ID " +
                "LEFT JOIN OFFERITEM oi ON o_oi.OFFERITEMS_ID = oi.ID " +
                "WHERE o.OFFERNUMBER like :offerNumber OR i.STATUS = :status GROUP BY i.id, o.OFFERNUMBER",
        resultClass = InvoiceDto.class,
        resultSetMapping = "invoiceDto")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Invoice {
    @Id
    @GeneratedValue
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 10)
    private InvoiceStatus status;

    @OneToOne
    private Offer offer;

    @OneToMany(mappedBy = "invoice")
    private Set<InvoiceItem> invoiceItems = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "seller_id", nullable = false)
    private Seller seller;

    @ManyToOne
    @JoinColumn(name = "buyer_id", nullable = false)
    private Buyer buyer;

    private Invoice(Buyer buyer, Seller seller, InvoiceStatus invoiceStatus) {
        this.buyer = buyer;
        this.seller = seller;
        status = invoiceStatus;
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

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", status=" + status +
                ", offer=" + offer +
                ", seller=" + seller.getId() +
                ", buyer=" + buyer.getId() +
                '}';
    }
}
