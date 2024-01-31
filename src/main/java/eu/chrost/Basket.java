package eu.chrost;

import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.UniqueConstraint;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@ToString
@NoArgsConstructor
@Entity
public class Basket {
    @Id
    @GeneratedValue
    private UUID id;

    @Convert(converter = BasketIdentifierConverter.class)
    @Column(name = "basket_id", nullable = false)
    private BasketIdentifier basketIdentifier;

    @ElementCollection
    @CollectionTable(
            uniqueConstraints = @UniqueConstraint(columnNames={"basket_id","product_id"})
    ) //optional but mandatory if unique constraint is needed
    @MapKeyColumn(name = "product_id") //optional
    @Column(name = "amount") //optional
    private Map<UUID, Integer> products = new HashMap<>();

    public Basket(BasketIdentifier basketIdentifier) {
        this.basketIdentifier = basketIdentifier;
    }

    public void addProducts(UUID id, int amount) {
        products.put(id, amount);
    }
}
