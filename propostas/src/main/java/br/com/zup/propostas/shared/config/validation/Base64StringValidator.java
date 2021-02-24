package br.com.zup.propostas.shared.config.validation;

import br.com.zup.propostas.biometryregistration.NewCardBiometryRequestDto;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class Base64StringValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return NewCardBiometryRequestDto.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        if(errors.hasErrors() || target == null) {
            return;
        }
        NewCardBiometryRequestDto request = (NewCardBiometryRequestDto) target;
        if (!Base64.isBase64(request.getFingerPrint())) {
            errors.rejectValue("fingerPrint", null, "Finger print must be sent in base64 encoding");
        }
    }

}
