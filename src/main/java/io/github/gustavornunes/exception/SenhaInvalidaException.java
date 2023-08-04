package io.github.gustavornunes.exception;

public class SenhaInvalidaException extends RuntimeException {

    public SenhaInvalidaException() {
        super("senha invalida");
    }
}
