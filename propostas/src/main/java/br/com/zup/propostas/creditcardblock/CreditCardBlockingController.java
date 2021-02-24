package br.com.zup.propostas.creditcardblock;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.creditcardrequest.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card/{id}/block")
public class CreditCardBlockingController {

    private final Logger logger = LoggerFactory.getLogger(CreditCardBlockingController.class);

    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CreditCardBlockingService creditCardBlockingService;

    @PostMapping
    public ResponseEntity block (@PathVariable("id") String creditCardId, HttpServletRequest httpRequest) {
        Optional<CreditCard> creditCard = creditCardRepository.findByNumber(creditCardId);
        if (!creditCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (creditCard.get().isCreditCardBlocked()) {
            String logMessage = "The credit card id " + creditCardId + " is already blocked!";
            logger.info(logMessage);
            return ResponseEntity.unprocessableEntity().body(logMessage);
        }

        String ipAddress = httpRequest.getHeader("X-Forward-For");
        if(ipAddress == null){
            ipAddress = httpRequest.getRemoteAddr();
        }
        logger.info("IP Address: " + ipAddress);

        creditCardBlockingService.blockCreditCard(creditCard.get(), ipAddress, httpRequest.getHeader(HttpHeaders.USER_AGENT));

        return ResponseEntity.ok().build();
    }
}
