package br.com.zup.propostas.creditcardblock;

import br.com.zup.propostas.creditcardblock.NotifyCreditCardBlockingClient.NotifyCreditCardBlockingResponseDto;
import br.com.zup.propostas.creditcardrequest.CreditCard;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CreditCardBlockingServiceTest {

    private CreditCard creditCard;
    private CreditCardBlockingService service;
    private NotifyCreditCardBlockingClient client;

    @BeforeEach
    public void before() {
        this.creditCard = new CreditCard("555", LocalDateTime.now(), "holder");
        this.client = mock(NotifyCreditCardBlockingClient.class);
        this.service = new CreditCardBlockingService(this.client, null, null);

    }

    @DisplayName("Must throw unprocessable entity (422) if something happens in external system")
    @Test
    void mustThrow422IfAnyErrorInExternalSystem (){
        FeignException.FeignServerException exception = mock(FeignException.FeignServerException.class);
        when(this.client.notifyCreditCardBlocking(any(), any()))
                .thenThrow(exception);

        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            service.blockCreditCard(this.creditCard, "", "");
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }

    @DisplayName("Must throw unprocessable entity (422) if external system return is not 'BLOQUEADO'")
    @Test
    void mustThrow422IfAnyExternalSystemReturnIsNotSuccess (){
        when(this.client.notifyCreditCardBlocking(any(), any()))
               .thenReturn(new NotifyCreditCardBlockingResponseDto(""));


        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            this.service.blockCreditCard(this.creditCard, "", "");
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }
}