package br.com.zup.propostas.biometryregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@RestController
@RequestMapping("/api/credit-card/{id}/biometry/{biometryId}")
public class BiometryConsultationController {

    @Autowired
    private CreditCardBiometryRepository biometryRepository;

    @GetMapping
    public ResponseEntity find (@PathVariable("id") String creditCardId, @PathVariable("biometryId") Long biometryId){
        Optional<CreditCardBiometry> biometry = this.biometryRepository.findByIdAndCreditCardNumber(biometryId, creditCardId);
        if(!biometry.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(new CreditCardBiometryResponseDto(biometry.get()));
    }

}
