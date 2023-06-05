package com.zerobase.dividend.config;

import javax.persistence.AttributeConverter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

public class StringListConverter implements AttributeConverter<List<String>, String> {

    private static final String SPLIT_CHAR = ",";

    @Override
    public String convertToDatabaseColumn(List<String> attributes) {
        return attributes != null ? String.join(SPLIT_CHAR, attributes) : null;
    }

    @Override
    public List<String> convertToEntityAttribute(String attribute) {
        return attribute != null ? Arrays.asList(attribute.split(SPLIT_CHAR)) : emptyList();
    }
}
