package br.com.zup.propostas.eletronicwalletregistration;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class CheckWalletCompanyValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return NewElectronicWalletRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors() || target == null) {
            return;
        }
        NewElectronicWalletRequestDto request = (NewElectronicWalletRequestDto) target;
        if(!WalletCompany.contains(request.getWallet())) {
            errors.rejectValue("wallet", null, "Wallet company not available!");
        }
    }
}
