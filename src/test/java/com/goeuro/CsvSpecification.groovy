package com.goeuro

import spock.lang.Specification

class CsvSpecification extends Specification {

    public File getTemporaryFile() {
        return File.createTempFile('testcsvfile', 'csv')
    }

    public List<String> readLinesFromFile(File file) {
        return file.readLines()
    }

    public void removeFile(File file) {
        if (file.exists()) {
            file.delete()
        }
    }
}