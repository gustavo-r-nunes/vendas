package io.github.gustavornunes.exception;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;
public class ApiErrors {

    public ApiErrors(String message) {
        this.errors = Arrays.asList(message);
    }

    public ApiErrors(List<String> errors) {
        this.errors = errors;
    }

    @Getter
    private List<String> errors;
}
