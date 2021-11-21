package com.smalaca.jpa.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ToDoCategoryConverter implements AttributeConverter<ToDoCategory, String> {
    private static final String NO_CATEGORY = "";
    private static final String SEPARATOR = ";";

    @Override
    public String convertToDatabaseColumn(ToDoCategory toDoCategory) {
        if (toDoCategory != null) {
            return toDoCategory.getShortName() + SEPARATOR + toDoCategory.getFullName();
        } else {
            return NO_CATEGORY;
        }
    }

    @Override
    public ToDoCategory convertToEntityAttribute(String value) {
        if (NO_CATEGORY.equals(value)) {
            return null;
        } else {
            String[] pieces = value.split(";");
            return new ToDoCategory(pieces[0], pieces[1]);
        }
    }
}
