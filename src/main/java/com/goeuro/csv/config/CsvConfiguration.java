package com.goeuro.csv.config;

import java.io.File;

public class CsvConfiguration {

    private Class<?> beanType;
    private File destinationFile;
    private String[] header;
    private String[] fieldMapping;

    public CsvConfiguration(Class<?> beanType, File destinationFile, String[] header, String[] fieldMapping) {
        this.beanType = beanType;
        this.destinationFile = destinationFile;
        this.header = header;
        this.fieldMapping = fieldMapping;
    }

    public Class<?> getBeanType() {
        return beanType;
    }

    public File getDestinationFile() {
        return destinationFile;
    }

    public String[] getHeader() {
        return header;
    }

    public String[] getFieldMapping() {
        return fieldMapping;
    }
}