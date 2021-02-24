package br.com.zup.propostas.proposalanalisys;

import br.com.zup.propostas.creditcardrequest.RequestCreditCardClient;
import br.com.zup.propostas.proposalregistration.Proposal;
import br.com.zup.propostas.proposalregistration.ProposalState;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProposalAnalysisService {

    private final Logger logger = LoggerFactory.getLogger(ProposalAnalysisService.class);
    private RequestAnalysisClient requestAnalysisClient;

    public ProposalAnalysisService(RequestAnalysisClient requestAnalysisClient) {
        this.requestAnalysisClient = requestAnalysisClient;
    }

    public void processProposalAnalysis(Proposal proposal) {
        try {
            ProposalAnalysisResponseDto analisys = this.requestAnalysisClient.sendRequestAnalysis(new ProposalAnalysisRequestDto(proposal));
            proposal.updateStatus(ProposalState.ELEGIBLE);
        } catch (FeignException.FeignClientException ex) {
            proposal.updateStatus(ProposalState.NOT_ELEGIBLE);
        }
    }

}
