package br.com.zup.propostas.travelwarningregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NewTravelWarningRequestDto {

    @NotBlank
    private String destination;
    @NotNull
    @Future
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate travelEndDate;

    public TravelWarning toModel(CreditCard creditCard, String ipAddress, String userAgent) {
        return new TravelWarning(creditCard, this.destination, this.travelEndDate, ipAddress, userAgent);
    }

    public String getDestination() {
        return destination;
    }

    public LocalDate getTravelEndDate() {
        return travelEndDate;
    }
}
