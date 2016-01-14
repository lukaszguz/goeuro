package com.goeuro;

import com.goeuro.validator.CityNameValidator;
import com.goeuro.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static Logger log = LoggerFactory.getLogger(Main.class);
    private static Validator<String> validator = new CityNameValidator();

    public static void main(String... args) {
        log.info("Start transformation");
        String cityName = args[0];
        if(validator.valid(cityName)) {
            Task task = new Task();
            task.run(cityName);
            log.info("End transformation");
        } else {
            log.error("Sorry, you should give correct city name");
        }
    }
}