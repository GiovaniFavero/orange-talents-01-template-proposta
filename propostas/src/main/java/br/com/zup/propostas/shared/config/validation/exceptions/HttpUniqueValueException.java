package br.com.zup.propostas.shared.config.validation.exceptions;

import org.springframework.validation.FieldError;

import java.util.List;

public class HttpUniqueValueException extends RuntimeException {

    private List<FieldError> fieldErrors;

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public HttpUniqueValueException(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
