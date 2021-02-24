package br.com.zup.propostas.creditcardblock;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.creditcardrequest.CreditCardRepository;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static br.com.zup.propostas.creditcardblock.NotifyCreditCardBlockingClient.NotifyCreditCardBlockingRequestDto;
import static br.com.zup.propostas.creditcardblock.NotifyCreditCardBlockingClient.NotifyCreditCardBlockingResponseDto;

@Service
public class CreditCardBlockingService {

    private final Logger logger = LoggerFactory.getLogger(CreditCardBlockingService.class);
    private NotifyCreditCardBlockingClient notifyCreditCardBlockingClient;
    private CreditCardRepository creditCardRepository;
    private CreditCardBlockingHistoryRepository creditCardBlockingHistoryRepository;

    public CreditCardBlockingService(NotifyCreditCardBlockingClient notifyCreditCardBlockingClient, CreditCardRepository creditCardRepository, CreditCardBlockingHistoryRepository creditCardBlockingHistoryRepository) {
        this.notifyCreditCardBlockingClient = notifyCreditCardBlockingClient;
        this.creditCardRepository = creditCardRepository;
        this.creditCardBlockingHistoryRepository = creditCardBlockingHistoryRepository;
    }

    public void blockCreditCard(CreditCard creditCard, String ipAddress, String userAgent) {

        String result = "";
        try {
            NotifyCreditCardBlockingResponseDto response =
                    this.notifyCreditCardBlockingClient.notifyCreditCardBlocking(new NotifyCreditCardBlockingRequestDto("Proposals"), creditCard.getNumber());
            result = response.getResult();
        } catch (FeignException.FeignServerException ex) {
            ex.printStackTrace();
            logger.error("The legacy system could not block the credit card!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The legacy system could not block the credit card!");
        }

        if ("BLOQUEADO".equals(result)) {
            this.updateCreditCardSituation(creditCard, ipAddress, userAgent);
            logger.info("The credit card was blocked successfully");
        } else {
            logger.error("The proposals system could not block the credit card!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The proposals system could not block the credit card!");
        }
    }

    public void updateCreditCardSituation (CreditCard creditCard, String ipAddress, String userAgent) {
        CreditCardBlockingHistory creditCardBlockingHistory = new CreditCardBlockingHistory(creditCard, ipAddress, userAgent);

        creditCardBlockingHistoryRepository.save(creditCardBlockingHistory);

        creditCard.block();
        creditCardRepository.save(creditCard);

    }
}