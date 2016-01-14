package com.goeuro;

import com.goeuro.client.HttpClient;
import com.goeuro.client.PositionRestClient;
import com.goeuro.csv.config.CsvConfiguration;
import com.goeuro.domain.GoEuroCity;
import com.goeuro.domain.GoEuroCsvInformationMapping;

import java.io.File;
import java.net.URI;

public class Task {

    private URI url = URI.create("http://api.goeuro.com/api/v2/position/suggest/en");
    private HttpClient client = new PositionRestClient(url);
    private CsvConfiguration csvConfig = createCsvConfgiuratioForGoEuroCity();

    public void run(String cityName) {
        TransformGoEuroCity transform = new TransformGoEuroCity(client,csvConfig);
        transform.transformation(cityName);
    }

    private CsvConfiguration createCsvConfgiuratioForGoEuroCity() {
        return new CsvConfiguration(
                GoEuroCity.class,
                new File("cities.csv"),
                GoEuroCsvInformationMapping.header,
                GoEuroCsvInformationMapping.fieldMapping
        );
    }
}
