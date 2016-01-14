package com.goeuro.validator;

import java.util.regex.Pattern;

public class CityNameValidator implements Validator<String> {

    private final static String CITY_NAME_REGEX = "^([a-zA-Z\\u0080-\\u024F]+(?:. |-| |'))*[a-zA-Z\\u0080-\\u024F]*$";
    private Pattern pattern = Pattern.compile(CITY_NAME_REGEX);

    @Override
    public boolean valid(String cityName) {
        return isNotEmpty(cityName) && pattern.matcher(cityName).matches();
    }

    private boolean isNotEmpty(String str) {
        return str != null && str.length() > 0;
    }
}
