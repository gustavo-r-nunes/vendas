package io.github.gustavornunes.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
public class ApiErrors {

    public ApiErrors(String message) {
        this.errors = Arrays.asList(message);
    }

    @Getter
    private List<String> errors;
}
