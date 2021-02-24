package br.com.zup.propostas.shared.config.validation;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Component
public class TesteValidator implements Validator {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public boolean supports(Class<?> clazz) {
        return Long.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors() || target == null) {
            return;
        }
        if((Long) target == 1) {
            errors.rejectValue("creditCardId", null, "Credit card not found!");
        }
    }
}
