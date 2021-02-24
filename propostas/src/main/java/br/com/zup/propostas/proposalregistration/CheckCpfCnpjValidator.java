package br.com.zup.propostas.proposalregistration;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CheckCpfCnpjValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewProposalRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (errors.hasErrors()) {
            return;
        }
        NewProposalRequestDto request = (NewProposalRequestDto) target;
        String document = request.getDocument();
        if(!request.isValidDocument()) {
            errors.rejectValue("document", null, "The document has to be a valid CPF or CNPJ!");
        }
    }
}