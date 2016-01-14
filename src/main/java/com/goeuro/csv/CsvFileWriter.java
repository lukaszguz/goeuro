package com.goeuro.csv;

import com.goeuro.csv.config.CsvConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.supercsv.io.dozer.CsvDozerBeanWriter;
import org.supercsv.io.dozer.ICsvDozerBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.Closeable;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter<T> implements Closeable {

    private static Logger log = LoggerFactory.getLogger(CsvFileWriter.class);
    private CsvConfiguration configuration;
    private ICsvDozerBeanWriter beanWriter;

    public CsvFileWriter(CsvConfiguration configuration) throws IOException {
        this.configuration = configuration;
        initializationBeanWriter();
    }

    private void initializationBeanWriter() throws IOException {
        beanWriter = new CsvDozerBeanWriter(
                new FileWriter(configuration.getDestinationFile()),
                CsvPreference.STANDARD_PREFERENCE
        );
        beanWriter.configureBeanMapping(configuration.getBeanType(), configuration.getFieldMapping());
    }

    @Override
    public void close() throws IOException {
        beanWriter.close();
    }

    public void writeBean(T bean) throws IOException {
        beanWriter.writeHeader(configuration.getHeader());
        beanWriter.write(bean);
    }

    public void writeList(List<T> beans) throws IOException {
        log.info("Start write information to csv file");
        beanWriter.writeHeader(configuration.getHeader());
        for (T bean : beans) {
            beanWriter.write(bean);
        }
        log.info("Finish write information to csv file");
    }
}