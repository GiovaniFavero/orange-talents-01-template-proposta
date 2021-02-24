package br.com.zup.propostas.eletronicwalletregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.creditcardrequest.CreditCardRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card/{id}/electronic-wallets")
public class ElectronicWalletRegistrationController {

    private final Logger logger = LoggerFactory.getLogger(ElectronicWalletRegistrationController.class);
    @Autowired
    private ElectronicWalletRegistrationService electronicWalletRegistrationService;
    @Autowired
    private CreditCardRepository creditCardRepository;
    @Autowired
    private CheckWalletCompanyValidator checkWalletCompanyValidator;

    @InitBinder
    public void init (WebDataBinder binder) {
        binder.addValidators(checkWalletCompanyValidator);
    }

    @PostMapping
    public ResponseEntity linkElectronicWallet (@PathVariable("id") String creditCardId,
                                                @RequestBody @Valid NewElectronicWalletRequestDto request, UriComponentsBuilder uriBuilder) {

        Optional<CreditCard> creditCard = creditCardRepository.findByNumber(creditCardId);
        if (!creditCard.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        if (electronicWalletRegistrationService.isElectronicWalletLinkedToCreditCard(creditCardId, WalletCompany.valueOf(request.getWallet()))) {
            logger.info("Wallet is already linked to this credit card!");
            return ResponseEntity.unprocessableEntity().build();
        }

        Long walletId = electronicWalletRegistrationService.linkElectronicWallet(creditCard.get(), request);

        URI location = uriBuilder.path("/api/credit-card/{id}/electronic-wallets/{idElectronicWallet}").buildAndExpand(creditCardId, walletId).toUri();
        return ResponseEntity.created(location).build();
    }

}
