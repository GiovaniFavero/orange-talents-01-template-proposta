package br.com.zup.propostas.shared.config.validation.annotations;

import br.com.zup.propostas.shared.config.validation.exceptions.HttpUniqueValueException;
import org.springframework.util.Assert;
import org.springframework.validation.FieldError;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private String domainAttribute;
    private Class<?> klass;
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        domainAttribute = constraintAnnotation.fieldName();
        klass = constraintAnnotation.domainClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Query query = entityManager.createQuery("select 1 from " + klass.getName() + " where " + domainAttribute + "=:value");
        query.setParameter("value", value);
        List<?> list = query.getResultList();
        String message = "The system found more than one " + klass.getSimpleName() + " with the attribute " + domainAttribute + " = " + value;
        Assert.state(list.size() <= 1, message);
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
        if (!list.isEmpty()) {
            throw new HttpUniqueValueException(Arrays.asList(new FieldError(this.klass.getSimpleName(), this.domainAttribute, "There's already a " + klass.getSimpleName() + " with this " + domainAttribute)));
        }
        return list.isEmpty();
    }
}