package br.com.zup.propostas.travelwarningregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.creditcardrequest.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card/{id}/travel-warnings")
public class TravelWarningRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(TravelWarningRegistrationController.class);
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private TravelWarningRegistrationService travelWarningRegistrationService;

    @PostMapping
    @Transactional
    public ResponseEntity create (@PathVariable("id") String creditCardId, @RequestBody @Valid NewTravelWarningRequestDto request, HttpServletRequest httpRequest) {
        Optional<CreditCard> creditCard = creditCardRepository.findByNumber(creditCardId);
        if (!creditCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        String ipAddress = httpRequest.getHeader("X-Forward-For");
        travelWarningRegistrationService.insertTravelWarning(request, creditCard.get(), ipAddress == null ? httpRequest.getRemoteAddr() : ipAddress, httpRequest.getHeader(HttpHeaders.USER_AGENT));
        return ResponseEntity.ok().build();
    }

}
