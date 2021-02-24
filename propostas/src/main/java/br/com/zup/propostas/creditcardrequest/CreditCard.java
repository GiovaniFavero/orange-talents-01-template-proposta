package br.com.zup.propostas.creditcardrequest;

import org.springframework.util.Assert;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class CreditCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String number;
    @NotNull
    private LocalDateTime registrationDate;
    private String holder;
    private boolean blocked;

    public CreditCard(@NotBlank String number, @NotNull LocalDateTime registrationDate, String holder) {
        Assert.hasLength(number, "[CreditCard] Number must not be blank!");

        this.number = number;
        this.registrationDate = registrationDate;
        this.holder = holder;
        this.blocked = false;
    }

    @Deprecated
    public CreditCard() {
    }

    public boolean isCreditCardBlocked() {
        return this.blocked;
    }

    public String getNumber() {
        return number;
    }

    public void block(){
        this.blocked = true;
    }
}
