package com.goeuro.validator

import spock.lang.Specification
import spock.lang.Unroll

class CityNameValidatorSpec extends Specification {

    private CityNameValidator validator = new CityNameValidator()

    @Unroll("Valid city name #cityName")
    def "Should valid city names"() {
        expect:
            validator.valid(cityName)

        where:
            cityName << ['Toronto', 'St. Catharines', 'San Fransisco',
            'Val-d\'Or', 'Presqu\'ile', 'Niagara on the Lake',
            'Niagara-on-the-Lake', 'München', 'toronto', 'toRonTo',
            'villes du Québec','Provence-Alpes-Côte d\'Azur',
            'Île-de-France', 'Kópavogur', 'Garðabær',
            'Sauðárkrókur', 'Þorlákshöfn']
    }

    @Unroll("Not valid city name #cityName")
    def "Should not valid city names"() {
        expect:
            !validator.valid(cityName)

        where:
            cityName << ['', ' ', '--', '-', '465465']
    }
}