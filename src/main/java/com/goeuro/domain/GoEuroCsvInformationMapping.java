package com.goeuro.domain;

public final class GoEuroCsvInformationMapping {
    public static final String[] header = { "_id", "name", "type", "latitude", "longitude" };
    public static final String[] fieldMapping = { "_id", "name", "type", "geo_position.latitude", "geo_position.longitude" };
}