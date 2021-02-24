package br.com.zup.propostas.eletronicwalletregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class ElectronicWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @Enumerated(EnumType.STRING)
    private WalletCompany wallet;
    @NotBlank
    private String email;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditCardId")
    private CreditCard creditCard;
    private String externalSystemId;

    public ElectronicWallet(@NotNull WalletCompany wallet, @NotBlank String email, @NotNull CreditCard creditCard, String externalSystemId) {
        Assert.notNull(wallet, "[ElectronicWallet] Wallet must not be null!");
        Assert.hasLength(email, "[ElectronicWallet] Email must not be empty!");
        Assert.notNull(creditCard, "[ElectronicWallet] CreditCard must not be null!");

        this.wallet = wallet;
        this.email = email;
        this.creditCard = creditCard;
        this.externalSystemId = externalSystemId;
    }

    @Deprecated
    public ElectronicWallet() {
    }

    public Long getId() {
        return id;
    }
}
