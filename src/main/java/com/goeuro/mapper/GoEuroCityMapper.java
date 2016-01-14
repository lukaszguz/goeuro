package com.goeuro.mapper;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.goeuro.domain.GoEuroCity;

import java.util.List;

public class GoEuroCityMapper {

    private ObjectMapper mapper = new ObjectMapper();

    public GoEuroCity mapToObject(String json) {
        return map(json, TypeFactory.defaultInstance().constructType(GoEuroCity.class));
    }

    public List<GoEuroCity> mapToList(String json) {
            return map(json, TypeFactory.defaultInstance().constructCollectionType(List.class, GoEuroCity.class));
    }

    private <T> T map(String json, JavaType valueType) {
        try {
            return mapper.readValue(json, valueType);
        } catch (Exception e) {
            throw new IllegalArgumentException("Couldn't map json to GoEuroCity class: " + json, e);
        }
    }
}