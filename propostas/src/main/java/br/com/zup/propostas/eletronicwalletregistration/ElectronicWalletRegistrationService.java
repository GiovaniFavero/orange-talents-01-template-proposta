package br.com.zup.propostas.eletronicwalletregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import static br.com.zup.propostas.eletronicwalletregistration.ElectronicWalletRegistrationClient.*;

@Service
public class ElectronicWalletRegistrationService {

    private final Logger logger = LoggerFactory.getLogger(ElectronicWalletRegistrationService.class);
    private ElectronicWalletRepository electronicWalletRepository;
    private ElectronicWalletRegistrationClient walletRegistrationClient;

    public ElectronicWalletRegistrationService(ElectronicWalletRepository electronicWalletRepository, ElectronicWalletRegistrationClient walletRegistrationClient) {
        this.electronicWalletRepository = electronicWalletRepository;
        this.walletRegistrationClient = walletRegistrationClient;
    }

    public boolean isElectronicWalletLinkedToCreditCard(String creditCardId, WalletCompany wallet) {
        return electronicWalletRepository.countByCreditCardNumberAndWallet(creditCardId, wallet) > 0;
    }

    public Long linkElectronicWallet(CreditCard creditCard, NewElectronicWalletRequestDto request) {
        String walletId = "";
        try {
            NotifyElectronicWalletResponseDto response = walletRegistrationClient.notifyElectronicWallet(new NotifyElectronicWalletRequestDto(request), creditCard.getNumber());
             if("ASSOCIADA".equals(response.getResult())) {
                 walletId = response.getId();
                 logger.info("External system linked electronic wallet successfully!");
             }
        } catch (FeignException.FeignServerException ex) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The legacy system could not link the electronic wallet!");
        }

        if (!walletId.isEmpty()) {
            ElectronicWallet electronicWallet = request.toModel(creditCard, walletId);
            electronicWalletRepository.save(electronicWallet);
            return electronicWallet.getId();
        }
        throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Something went wrong when trying to link the electronic wallet!");
    }
}
