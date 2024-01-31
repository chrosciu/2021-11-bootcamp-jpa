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

    public static Invoice created() {
        Invoice invoice = new Invoice();
        invoice.status = InvoiceStatus.CREATED;
        return invoice;
    }

    UUID getId() {
        return id;
    }
}
