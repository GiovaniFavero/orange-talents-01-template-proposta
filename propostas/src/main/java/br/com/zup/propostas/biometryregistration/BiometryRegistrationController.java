package br.com.zup.propostas.biometryregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.creditcardrequest.CreditCardRepository;
import br.com.zup.propostas.shared.config.validation.Base64StringValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card/{id}/biometry")
public class BiometryRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(BiometryRegistrationController.class);
    @Autowired
    private CreditCardRepository creditCardRepository;
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private Base64StringValidator base64StringValidator;

    @InitBinder
    public void init (WebDataBinder binder) {
        binder.addValidators(base64StringValidator);
    }

    @PostMapping
    @Transactional
    public ResponseEntity create (@PathVariable("id") String creditCardId, @RequestBody @Valid NewCardBiometryRequestDto request, UriComponentsBuilder uriBuilder) {
        Optional<CreditCard> creditCard = creditCardRepository.findByNumber(creditCardId);
        if (!creditCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        CreditCardBiometry biometry = request.toModel(creditCard.get());
        entityManager.persist(biometry);
        URI location = uriBuilder.path("/api/credit-card/{id}/biometry/{id}").buildAndExpand(creditCardId, biometry.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

}
