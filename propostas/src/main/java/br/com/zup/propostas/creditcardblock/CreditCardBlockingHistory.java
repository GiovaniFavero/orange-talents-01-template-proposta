package br.com.zup.propostas.creditcardblock;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CreditCardBlockingHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditCardId")
    private CreditCard creditCard;
    @NotBlank
    private String ipAddress;
    @NotNull
    private LocalDateTime blockingDate;
    private String userAgent;

    public CreditCardBlockingHistory(@NotNull CreditCard creditCard, @NotBlank String ipAddress, String userAgent) {
        Assert.notNull(creditCard, "[CreditCardBlockingHistory] Credit card must not be null!");

        this.creditCard = creditCard;
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
        this.blockingDate = LocalDateTime.now();
    }

    @Deprecated
    public CreditCardBlockingHistory() {
    }
}
