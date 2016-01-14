package com.goeuro.validator;

public interface Validator<T> {
    boolean valid(T object);
}
