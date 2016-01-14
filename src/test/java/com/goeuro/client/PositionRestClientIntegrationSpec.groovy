package com.goeuro.client

import com.goeuro.exception.ConnectException
import spock.lang.Specification

class PositionRestClientIntegrationSpec extends Specification {

    def "Should get response from GoEuro position api for Berlin city"() {
        given:
            URI url = URI.create("http://api.goeuro.com/api/v2/position/suggest/en")
            PositionRestClient client = new PositionRestClient(url);
            String cityName = "Berlin"

        when:
            String response = client.sendRequest(cityName)

        then:
            response == '[{"_id":376217,"key":null,"name":"Berlin","fullName":"Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":52.52437,"longitude":13.41053},"locationId":8384,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":448103,"key":null,"name":"Berlingo","fullName":"Berlingo, Italy","iata_airport_code":null,"type":"location","country":"Italy","geo_position":{"latitude":45.50298,"longitude":10.04366},"locationId":147721,"inEurope":true,"countryCode":"IT","coreCountry":true,"distance":null},{"_id":425332,"key":null,"name":"Berlingerode","fullName":"Berlingerode, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":51.45775,"longitude":10.2384},"locationId":124675,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":425326,"key":null,"name":"Bernau bei Berlin","fullName":"Bernau bei Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":52.67982,"longitude":13.58708},"locationId":124669,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":314826,"key":null,"name":"Berlin Tegel","fullName":"Berlin Tegel (TXL), Germany","iata_airport_code":"TXL","type":"airport","country":"Germany","geo_position":{"latitude":52.5548,"longitude":13.28903},"locationId":null,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":314827,"key":null,"name":"Berlin Schönefeld","fullName":"Berlin Schönefeld (SXF), Germany","iata_airport_code":"SXF","type":"airport","country":"Germany","geo_position":{"latitude":52.3887261,"longitude":13.5180874},"locationId":null,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":334196,"key":null,"name":"Berlin Hbf","fullName":"Berlin Hbf, Germany","iata_airport_code":null,"type":"station","country":"Germany","geo_position":{"latitude":52.525589,"longitude":13.369548},"locationId":null,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},{"_id":333977,"key":null,"name":"Berlin Ostbahnhof","fullName":"Berlin Ostbahnhof, Germany","iata_airport_code":null,"type":"station","country":"Germany","geo_position":{"latitude":52.510972,"longitude":13.434567},"locationId":null,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null}]'
    }

    def "Should thrown ConnectException when URI is not correctly"() {
        given:
            URI notCorrectAddress = URI.create("notcorrect")
            PositionRestClient client = new PositionRestClient(notCorrectAddress);
            String cityName = "Berlin"

        when:
            client.sendRequest(cityName)

        then:
            thrown(ConnectException)
    }
}