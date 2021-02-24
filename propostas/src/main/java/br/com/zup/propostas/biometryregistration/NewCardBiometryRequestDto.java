package br.com.zup.propostas.biometryregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;

import javax.validation.constraints.NotBlank;

public class NewCardBiometryRequestDto {

    @NotBlank
    private String fingerPrint;

    public String getFingerPrint() {
        return fingerPrint;
    }

    public CreditCardBiometry toModel(CreditCard creditCard) {
        return new CreditCardBiometry(creditCard, this.fingerPrint);
    }
}
