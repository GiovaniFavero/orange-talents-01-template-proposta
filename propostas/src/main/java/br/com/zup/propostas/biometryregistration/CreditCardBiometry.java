package br.com.zup.propostas.biometryregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CreditCardBiometry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String fingerPrint;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditCardId")
    private CreditCard creditCard;
    @NotNull
    private LocalDateTime registrationDate;

    @Deprecated
    public CreditCardBiometry() {
    }

    public CreditCardBiometry(CreditCard creditCard, String fingerPrint) {
        Assert.notNull(creditCard, "[ProposalBiometry] CreditCard must not be null!");
        Assert.hasLength(fingerPrint, "[ProposalBiometry] Finger print must not be blank!");

        this.creditCard = creditCard;
        this.fingerPrint = fingerPrint;
        this.registrationDate = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public String getFingerPrint() {
        return fingerPrint;
    }
}
