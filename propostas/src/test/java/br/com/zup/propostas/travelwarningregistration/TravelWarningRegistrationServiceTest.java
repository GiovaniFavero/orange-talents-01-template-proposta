package br.com.zup.propostas.travelwarningregistration;

import br.com.zup.propostas.creditcardblock.CreditCardBlockingService;
import br.com.zup.propostas.creditcardblock.NotifyCreditCardBlockingClient;
import br.com.zup.propostas.creditcardrequest.CreditCard;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TravelWarningRegistrationServiceTest {

    private CreditCard creditCard;
    private TravelWarningRegistrationService service;
    private NotifyTravelWarningRequestClient client;

    @BeforeEach
    public void before() {
        this.creditCard = new CreditCard("555", LocalDateTime.now(), "holder");
        this.client = mock(NotifyTravelWarningRequestClient.class);
        this.service = new TravelWarningRegistrationService(this.client, null);

    }

    @DisplayName("Must throw unprocessable entity (422) if something happens in external system")
    @Test
    void mustThrow422IfAnyErrorInExternalSystem (){
        FeignException.FeignServerException exception = mock(FeignException.FeignServerException.class);
        when(this.client.notifyTravelWarning(any(), any()))
                .thenThrow(exception);

        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            service.insertTravelWarning(new NewTravelWarningRequestDto(), this.creditCard, "", "");
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }

    @DisplayName("Must throw unprocessable entity (422) if external system return is not 'CRIADO'")
    @Test
    void mustThrow422IfAnyExternalSystemReturnIsNotSuccess (){
        when(this.client.notifyTravelWarning(any(), any()))
                .thenReturn(new NotifyTravelWarningRequestClient.NotifyTravelWarningResponseDto(""));

        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            this.service.insertTravelWarning(new NewTravelWarningRequestDto(), this.creditCard, "", "");
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }

}