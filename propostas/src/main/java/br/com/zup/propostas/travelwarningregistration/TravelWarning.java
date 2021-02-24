package br.com.zup.propostas.travelwarningregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class TravelWarning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    @ManyToOne
    @JoinColumn(name = "creditCardId")
    private CreditCard creditCard;
    @NotBlank
    private String destination;
    @NotNull @Future
    private LocalDate travelEndDate;
    @NotNull
    private LocalDateTime registrationDate;
    private String ipAddress;
    private String userAgent;

    @Deprecated
    public TravelWarning() {
    }

    public TravelWarning(@NotNull CreditCard creditCard, @NotBlank String destination, @NotNull @Future LocalDate travelEndDate, String ipAddress, String userAgent) {
        Assert.notNull(creditCard, "[TravelWarning] Credit card must not be null!");
        Assert.hasLength(destination, "[TravelWarning] Destination must not be blank!");
        Assert.notNull(travelEndDate, "[TravelWarning] Travel end date must not be null!");
        Assert.isTrue(LocalDate.now().isBefore(travelEndDate), "[TravelWarning] Travel end date must be in the future!");

        this.creditCard = creditCard;
        this.destination = destination;
        this.travelEndDate = travelEndDate;
        this.registrationDate = LocalDateTime.now();
        this.ipAddress = ipAddress;
        this.userAgent = userAgent;
    }
}
