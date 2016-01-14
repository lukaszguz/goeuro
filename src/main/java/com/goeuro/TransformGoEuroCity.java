package com.goeuro;

import com.goeuro.client.HttpClient;
import com.goeuro.csv.config.CsvConfiguration;
import com.goeuro.csv.CsvFileWriter;
import com.goeuro.domain.GoEuroCity;
import com.goeuro.exception.TransformationException;
import com.goeuro.mapper.GoEuroCityMapper;

import java.util.List;

public class TransformGoEuroCity {

    private HttpClient client;
    private CsvConfiguration csvConfiguration;
    private GoEuroCityMapper mapper = new GoEuroCityMapper();

    public TransformGoEuroCity(HttpClient client, CsvConfiguration csvConfiguration) {
        this.client = client;
        this.csvConfiguration = csvConfiguration;
    }

    public void transformation(String cityName) {
        String json = downloadJson(cityName);
        List<GoEuroCity> goEuroCities = mapper.mapToList(json);
        writeGoEuroCitiesToFile(goEuroCities);
    }

    private String downloadJson(String cityName) {
        return client.sendRequest(cityName);
    }

    private void writeGoEuroCitiesToFile(List<GoEuroCity> goEuroCities) {
        try (CsvFileWriter<GoEuroCity> writer = new CsvFileWriter<>(csvConfiguration))  {
            writer.writeList(goEuroCities);
        } catch (Exception e) {
            throw new TransformationException(e);
        }
    }
}