package br.com.zup.propostas.travelwarningregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import br.com.zup.propostas.travelwarningregistration.NotifyTravelWarningRequestClient.*;
import org.springframework.web.server.ResponseStatusException;

@Service
public class TravelWarningRegistrationService {

    private final Logger logger = LoggerFactory.getLogger(TravelWarningRegistrationService.class);
    private NotifyTravelWarningRequestClient warningRequestClient;
    private TravelWarningRepository travelWarningRepository;

    public TravelWarningRegistrationService(NotifyTravelWarningRequestClient warningRequestClient, TravelWarningRepository travelWarningRepository) {
        this.warningRequestClient = warningRequestClient;
        this.travelWarningRepository = travelWarningRepository;
    }

    public void insertTravelWarning(NewTravelWarningRequestDto request, CreditCard creditCard, String ipAddress, String userAgent) {

        String result = "";
        try {
            NotifyTravelWarningResponseDto response = warningRequestClient.notifyTravelWarning(new NotifyTravelWarningRequestDto(request.getDestination(), request.getTravelEndDate()), creditCard.getNumber());
            result = response.getResult();
        } catch (FeignException.FeignServerException ex) {
            logger.error("The legacy system could not receive the travel warning!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The legacy system could not receive the travel warning!");
        }
        if ("CRIADO".equals(result)) {
            TravelWarning travelWarning = request.toModel(creditCard, ipAddress,userAgent);
            travelWarningRepository.save(travelWarning);
            logger.info("Travel warning created successfully!");
        } else {
            logger.error("The legacy system could not receive the travel warning!");
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "The legacy system could not receive the travel warning!");
        }

    }
}
