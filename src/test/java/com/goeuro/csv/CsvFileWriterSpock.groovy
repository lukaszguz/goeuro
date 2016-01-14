package com.goeuro.csv

import com.goeuro.CsvSpecification
import com.goeuro.csv.config.CsvConfiguration
import com.goeuro.domain.GeoPosition
import com.goeuro.domain.GoEuroCity
import com.goeuro.domain.GoEuroCsvInformationMapping

class CsvFileWriterSpock extends CsvSpecification {

    private String[] header = GoEuroCsvInformationMapping.header
    private String[] fieldMapping = GoEuroCsvInformationMapping.fieldMapping

    def "Should write GoEuroCity to temporary file"() {
        given:
            File temporaryFile = getTemporaryFile()
            CsvConfiguration configuration = new CsvConfiguration(GoEuroCity, temporaryFile, header, fieldMapping)

            CsvFileWriter writer = new CsvFileWriter(configuration)
            GoEuroCity goEuroCity = getGoEuroCity(1L);

        when:
            try {
                writer.writeBean(goEuroCity)
            } finally {
                writer.close()
            }

        then:
            temporaryFile.exists()
            List<String> linesFromFile = readLinesFromFile(temporaryFile)
            linesFromFile[0] == '_id,name,type,latitude,longitude'
            linesFromFile[1] == '1,name,type,1.11,2.22'

        cleanup:
            removeFile(temporaryFile)
    }

    def "Should write two object GoEuroCity to temporary file"() {
        given:
            File temporaryFile = File.createTempFile('testcsvfile', 'csv')
            CsvConfiguration configuration = new CsvConfiguration(GoEuroCity, temporaryFile, header, fieldMapping)

            CsvFileWriter writer = new CsvFileWriter(configuration)
            List<GoEuroCity> goEuroCities = [getGoEuroCity(1L), getGoEuroCity(2L)]

        when:
            try {
                writer.writeList(goEuroCities)
            } finally {
                writer.close()
            }

        then:
            temporaryFile.exists()
            List<String> linesFromFile = readLinesFromFile(temporaryFile)
            linesFromFile[0] == '_id,name,type,latitude,longitude'
            linesFromFile[1] == '1,name,type,1.11,2.22'
            linesFromFile[2] == '2,name,type,1.11,2.22'

        cleanup:
            removeFile(temporaryFile)
    }

    private GoEuroCity getGoEuroCity(Long id) {
         return new GoEuroCity(
                 _id: id, name: 'name', type: 'type',
                 geo_position: new GeoPosition(latitude: new BigDecimal('1.11'), longitude: new BigDecimal('2.22'))
        )
    }
}