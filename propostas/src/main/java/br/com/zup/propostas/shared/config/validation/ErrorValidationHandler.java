package br.com.zup.propostas.shared.config.validation;

import br.com.zup.propostas.shared.config.validation.exceptions.HttpExistsObjectWithFieldException;
import br.com.zup.propostas.shared.config.validation.exceptions.HttpUniqueValueException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorValidationHandler {

    @Autowired
    private MessageSource messageSource;

    public List<ErrorFormDto> handleErrors (List<FieldError> fieldErrors) {
        List<ErrorFormDto> dto = new ArrayList<>();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e, LocaleContextHolder.getLocale());
            ErrorFormDto error = new ErrorFormDto(e.getField(), message);
            dto.add(error);
        });
        return dto;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormDto> handle(MethodArgumentNotValidException exception) {
        return this.handleErrors(exception.getBindingResult().getFieldErrors());
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public List<ErrorFormDto> handle(BindException exception) {
        return this.handleErrors(exception.getBindingResult().getFieldErrors());
    }

    @ResponseStatus(code = HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(HttpUniqueValueException.class)
    public List<ErrorFormDto> handle(HttpUniqueValueException exception) {
        return this.handleErrors(exception.getFieldErrors());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(HttpExistsObjectWithFieldException.class)
    public List<ErrorFormDto> handle(HttpExistsObjectWithFieldException exception) {
        return this.handleErrors(exception.getFieldErrors());
    }
}