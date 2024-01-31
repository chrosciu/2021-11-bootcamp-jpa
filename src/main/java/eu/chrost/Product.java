package eu.chrost;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
@Getter
public class Product {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    private String description;

    @ElementCollection
    @CollectionTable(name = "categories") //optional
    @Column(name = "category") //optional
    private Set<String> categories = new HashSet<>();

    public Product(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public void changeDescriptionTo(String description) {
        this.description = description;
    }

    public void addCategory(String category) {
        categories.add(category);
    }
}
