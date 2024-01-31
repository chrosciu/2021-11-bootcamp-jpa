package eu.chrost;

import javax.persistence.AttributeConverter;
import java.time.LocalDate;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

public class BasketIdentifierConverter implements AttributeConverter<BasketIdentifier, String> {
    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(BasketIdentifier basketIdentifier) {
        return basketIdentifier.getLogin() + SEPARATOR +
                basketIdentifier.getVisits() + SEPARATOR +
                basketIdentifier.getCreationDate().format(BASIC_ISO_DATE);
    }

    @Override
    public BasketIdentifier convertToEntityAttribute(String value) {
        String[] pieces = value.split(SEPARATOR);
        return new BasketIdentifier(pieces[0], Integer.parseInt(pieces[1]), LocalDate.parse(pieces[2], BASIC_ISO_DATE));
    }
}
