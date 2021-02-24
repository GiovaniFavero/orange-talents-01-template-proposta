package br.com.zup.propostas.eletronicwalletregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class NewElectronicWalletRequestDto {

    @NotBlank
    @Email
    private String email;
    @NotNull
    private String wallet;

    public String getEmail() {
        return email;
    }

    public String getWallet() {
        return wallet;
    }

    public NewElectronicWalletRequestDto(@NotBlank @Email String email, @NotNull String wallet) {
        this.email = email;
        this.wallet = wallet;
    }

    public ElectronicWallet toModel(CreditCard creditCard, String externalSystemId) {
        return new ElectronicWallet(WalletCompany.valueOf(this.wallet), this.email, creditCard, externalSystemId);
    }
}
