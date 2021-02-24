package br.com.zup.propostas.creditcardrequest;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

    Optional<CreditCard> findByNumber(String number);

}
