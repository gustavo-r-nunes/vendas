package io.github.gustavornunes.controller;

import io.github.gustavornunes.exception.ApiErrors;
import io.github.gustavornunes.exception.RegraNegocioException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class ApplicationControllerAdvice {

    @ExceptionHandler(RegraNegocioException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ApiErrors handleRegraNegocioException(RegraNegocioException exception){
       String mensagemErro = exception.getMessage();
       return new ApiErrors(mensagemErro);
    }
}
