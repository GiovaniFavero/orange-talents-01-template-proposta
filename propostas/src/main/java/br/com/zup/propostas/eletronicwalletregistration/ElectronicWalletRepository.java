package br.com.zup.propostas.eletronicwalletregistration;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ElectronicWalletRepository extends JpaRepository<ElectronicWallet, Long> {

    Long countByCreditCardNumberAndWallet(String creditCardId, WalletCompany walletCompany);

}
