package com.goeuro.mapper

import com.goeuro.domain.GoEuroCity
import spock.lang.Specification

class GoEuroCityMapperSpec extends Specification {

    private GoEuroCityMapper mapper

    def setup() {
        mapper = new GoEuroCityMapper();
    }

    def "Should map json to GoEuroCity object"() {
        given:
            String entityJson = '{"_id":376217,"key":null,"name":"Berlin","fullName":"Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":52.52437,"longitude":13.41053},"locationId":8384,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null}'

        when:
            GoEuroCity city = mapper.mapToObject(entityJson)

        then:
            city._id == 376217L
            city.name == 'Berlin'
            city.type == 'location'
            city.geo_position.latitude == new BigDecimal('52.52437')
            city.geo_position.longitude == new BigDecimal('13.41053')
    }

    def "Should thrown IllegalArgumentException when json is not correctly"() {
        when:
            mapper.mapToObject(json)

        then:
            thrown(IllegalArgumentException)

        where:
            json << ['', '[]', '  ', 'abc']
    }

    def "Should map json contain arrays to list of GoEuroCity objects"() {
        given:
            String entityJson = '''[
                {"_id":123,"key":null,"name":"Berlin","fullName":"Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":52.52437,"longitude":13.41053},"locationId":8384,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null},
                {"_id":456,"key":null,"name":"Berlin","fullName":"Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":52.52437,"longitude":13.41053},"locationId":8384,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null}
            ]'''

        when:
            List<GoEuroCity> cities = mapper.mapToList(entityJson)

        then:
            cities.each { GoEuroCity city ->
                city._id == expectedId
                city.name == 'Berlin'
                city.type == 'location'
                city.geo_position.latitude == new BigDecimal('52.52437')
                city.geo_position.longitude == new BigDecimal('13.41053')
                city.name == 'Berlin'

            }

        where:
            expectedId << [123L, 457L]
    }

    def "Should return empty list when json array is empty"() {
        given:
            String entityJson = '[]'

        when:
            List<GoEuroCity> cities = mapper.mapToList(entityJson)

        then:
            cities == []
    }

    def "Should thrown IllegalArgumentException when json is empty and we want list of objects"() {
        when:
            mapper.mapToList(json)

        then:
            thrown(IllegalArgumentException)

        where:
            json << ['', 'abc', '  ']
    }
}