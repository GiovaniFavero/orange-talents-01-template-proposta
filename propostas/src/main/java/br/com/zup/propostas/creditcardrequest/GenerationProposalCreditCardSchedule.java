package br.com.zup.propostas.creditcardrequest;

import br.com.zup.propostas.proposalregistration.Proposal;
import br.com.zup.propostas.proposalregistration.ProposalRepository;
import br.com.zup.propostas.proposalregistration.ProposalState;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Configuration
@EnableAsync
@EnableScheduling
public class GenerationProposalCreditCardSchedule {

    private final Logger logger = LoggerFactory.getLogger(GenerationProposalCreditCardSchedule.class);
    private ProposalRepository proposalRepository;
    private RequestCreditCardClient requestCreditCardClient;

    public GenerationProposalCreditCardSchedule(ProposalRepository proposalRepository, RequestCreditCardClient requestCreditCardClient) {
        this.proposalRepository = proposalRepository;
        this.requestCreditCardClient = requestCreditCardClient;
    }

    @Scheduled(fixedDelay = 5000)
    public void checkCreditCardGeneration() {
        List<Proposal> proposals = proposalRepository.findTop100ByStateOrderByIdAsc(ProposalState.ELEGIBLE);
        proposals.forEach(proposal -> {
            try {
                CreditCardResponseDto response = requestCreditCardClient.checkGeneratedCreditCard(proposal.getId());
                CreditCard creditCard = new CreditCard(response.getId(), response.getRegistrationDate(), response.getHolder());
                proposal.updateCreditCard(creditCard);
                proposalRepository.save(proposal);
            } catch (FeignException.FeignServerException e) {
                logger.info("Credit card to proposal " + proposal.getId() + " not generated yet!");
            }
        });
    }


}
