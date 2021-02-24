package br.com.zup.propostas.biometryregistration;

public class CreditCardBiometryResponseDto {

    private String creditCardId;
    private String fingerPrint;

    public CreditCardBiometryResponseDto(CreditCardBiometry creditCardBiometry) {
        this.creditCardId = creditCardBiometry.getCreditCard().getNumber();
        this.fingerPrint = creditCardBiometry.getFingerPrint();
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
