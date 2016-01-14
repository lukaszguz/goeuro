package com.goeuro

import com.goeuro.client.HttpClient
import com.goeuro.csv.config.CsvConfiguration
import com.goeuro.domain.GoEuroCity
import com.goeuro.domain.GoEuroCsvInformationMapping
import com.goeuro.exception.ConnectException
import com.goeuro.exception.TransformationException

class TransformGoEuroCitySpec extends CsvSpecification {

    private String cityName = 'Berlin'
    private String json = '[{"_id":1,"key":null,"name":"Berlin","fullName":"Berlin, Germany","iata_airport_code":null,"type":"location","country":"Germany","geo_position":{"latitude":1.11,"longitude":2.22},"locationId":8384,"inEurope":true,"countryCode":"DE","coreCountry":true,"distance":null}]'
    private HttpClient clientMock = Mock(HttpClient)

    def setup() {
        clientMock.sendRequest(cityName) >> json
    }

    def "Should produce csv file with GoEuroCity information"() {
        given:
            File temporaryFile = getTemporaryFile()
            CsvConfiguration configuration = getCsvConfiguration(temporaryFile)
            TransformGoEuroCity transform = new TransformGoEuroCity(clientMock, configuration)

        when:
            transform.transformation(cityName)

        then:
            temporaryFile.exists()
            List<String> linesFromFile = readLinesFromFile(temporaryFile)
            linesFromFile[0] == '_id,name,type,latitude,longitude'
            linesFromFile[1] == '1,Berlin,location,1.11,2.22'

        cleanup:
            removeFile(temporaryFile)
    }

    def "Should throw ConnectException when HttpClient can't connect to resource"() {
        given:
            CsvConfiguration configuration = getCsvConfiguration(temporaryFile)
            TransformGoEuroCity transform = new TransformGoEuroCity(clientMock, configuration)

        when:
            transform.transformation(cityName)

        then:
            clientMock.sendRequest(_) >> { throw new ConnectException()}
            thrown(ConnectException)
    }

    def "Should throw IllegalArgumentException when json from HttpClient is not correctly"() {
        given:
            String cityName = 'cityName'
            CsvConfiguration configuration = getCsvConfiguration(temporaryFile)
            TransformGoEuroCity transform = new TransformGoEuroCity(clientMock, configuration)

        when:
            transform.transformation(cityName)

        then:
            clientMock.sendRequest(cityName) >> json
            thrown(IllegalArgumentException)

        where:
            json << ['[abc]', 'abc', ' ', '']
    }

    def "Should throw TransformGoEuroCity when problem is connection with write to csv file"() {
        given:
            File temporaryFile = null
            CsvConfiguration configuration = getCsvConfiguration(temporaryFile)
            TransformGoEuroCity transform = new TransformGoEuroCity(clientMock, configuration)

        when:
            transform.transformation(cityName)

        then:
            thrown(TransformationException)
    }

    private CsvConfiguration getCsvConfiguration(File destinationFile) {
        return new CsvConfiguration(
                GoEuroCity,
                destinationFile,
                GoEuroCsvInformationMapping.header,
                GoEuroCsvInformationMapping.fieldMapping
        )
    }
}