package br.com.zup.propostas.eletronicwalletregistration;

import br.com.zup.propostas.creditcardblock.NotifyCreditCardBlockingClient;
import br.com.zup.propostas.creditcardrequest.CreditCard;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ElectronicWalletRegistrationServiceTest {

    private ElectronicWalletRegistrationClient client;
    private ElectronicWalletRegistrationService service;
    private CreditCard creditCard;
    private NewElectronicWalletRequestDto requestDto;
    private ElectronicWalletRepository electronicWalletRepository;

    @BeforeEach
    public void before() {
        this.creditCard = new CreditCard("555", LocalDateTime.now(), "holder");
        this.client = Mockito.mock(ElectronicWalletRegistrationClient.class);
        this.electronicWalletRepository = Mockito.mock(ElectronicWalletRepository.class);
        this.service = new ElectronicWalletRegistrationService(null, this.client);
        this.requestDto = new NewElectronicWalletRequestDto("email@email.com", "PAYPAL");
    }

    @DisplayName("Must throw unprocessable entity (422) if something happens in external system")
    @Test
    void mustThrow422IfAnyErrorInExternalSystem (){
        FeignException.FeignServerException exception = mock(FeignException.FeignServerException.class);
        when(this.client.notifyElectronicWallet(any(), any()))
                .thenThrow(exception);

        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            service.linkElectronicWallet(this.creditCard, this.requestDto);
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }

    @DisplayName("Must throw unprocessable entity (422) if external system doesn't return wallet ID")
    @Test
    void mustThrow422IfAnyExternalSystemReturnIsNotSuccess (){
        when(this.client.notifyElectronicWallet(any(), any()))
                .thenReturn(new ElectronicWalletRegistrationClient.NotifyElectronicWalletResponseDto("ASSOCIADA", ""));


        ResponseStatusException statusException = assertThrows(ResponseStatusException.class, () -> {
            this.service.linkElectronicWallet(this.creditCard, this.requestDto);
        });
        assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, statusException.getStatus());
    }

}