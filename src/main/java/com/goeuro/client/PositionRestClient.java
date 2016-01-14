package com.goeuro.client;

import com.goeuro.exception.ConnectException;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.net.URISyntaxException;

public class PositionRestClient implements HttpClient {

    private static Logger log = LoggerFactory.getLogger(PositionRestClient.class);
    private URI uri;

    public PositionRestClient(URI uri) {
        this.uri = uri;
    }

    @Override
    public String sendRequest(String cityName) {
        try {
            log.info("Star download information from GoEuro api");
            String json = getRequest(cityName).execute().returnContent().asString();
            log.info("Finish download information");
            return json;
        } catch (Exception e) {
            log.error("Problem with remote resource", e);
            throw new ConnectException(e);
        }
    }

    private Request getRequest(String cityName) throws URISyntaxException {
        URI uriWithCityName = getUriWithCityName(cityName);
        return Request.Get(uriWithCityName)
                .connectTimeout(1000)
                .socketTimeout(1000);
    }

    private URI getUriWithCityName(String cityName) throws URISyntaxException {
        URIBuilder builder = new URIBuilder(uri);
        builder.setPath(builder.getPath() + "/" + cityName);
        return builder.build();
    }
}