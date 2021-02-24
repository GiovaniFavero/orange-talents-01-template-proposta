package br.com.zup.propostas.creditcardrequest;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CreditCardResponseDto {

    @JsonProperty("id")
    private String id;
    @JsonProperty("emitidoEm")
    private LocalDateTime registrationDate;
    @JsonProperty("titular")
    private String holder;

    public String getId() {
        return id;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public String getHolder() {
        return holder;
    }
}
