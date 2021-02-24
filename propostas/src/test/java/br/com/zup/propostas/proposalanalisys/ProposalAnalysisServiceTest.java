package br.com.zup.propostas.proposalanalisys;

import br.com.zup.propostas.proposalregistration.Proposal;
import br.com.zup.propostas.proposalregistration.ProposalState;
import br.com.zup.propostas.shared.config.security.CustomEncryptor;
import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ProposalAnalysisServiceTest {

    private RequestAnalysisClient client;
    private ProposalAnalysisService service;

    @BeforeEach
    public void before (){
        this.client = mock(RequestAnalysisClient.class);
        this.service = new ProposalAnalysisService(this.client);
        CustomEncryptor.testCustomEncryptor("9y$B&E)H@McQfTjWnZr4u7x!z%C*F-JaNdRgUkXp2s5v8y/B?D(G+KbPeShVmYq3", "84a3db7f7b47bc13");
    }

    @DisplayName("Must set proposal state to 'NOT_ELIGIBLE' if something happens in external system")
    @Test
    void mustSetProposalStateToNOT_ELIGIBLEIfAnyErrorInExternalSystem (){
        FeignException.FeignClientException exception = mock(FeignException.FeignClientException.class);

        when(this.client.sendRequestAnalysis(any()))
                .thenThrow(exception);

        Proposal proposal = new Proposal(CustomEncryptor.getInstance().encrypt("document"), "email", "name", "address", new BigDecimal(1));
        proposal.updateStatus(ProposalState.ELEGIBLE);
        service.processProposalAnalysis(proposal);

        assertEquals(ProposalState.NOT_ELEGIBLE, proposal.getState());
    }

    @DisplayName("Must set proposal state to 'ELIGIBLE' if success with external system")
    @Test
    void mustSetProposalStateToELIGIBLEIfSuccessWithExternalSystem (){
        FeignException.FeignClientException exception = mock(FeignException.FeignClientException.class);

        when(this.client.sendRequestAnalysis(any()))
                .thenReturn(new ProposalAnalysisResponseDto());

        Proposal proposal = new Proposal(CustomEncryptor.getInstance().encrypt("document"), "email", "name", "address", new BigDecimal(1));
        service.processProposalAnalysis(proposal);

        assertEquals(ProposalState.ELEGIBLE, proposal.getState());
    }

}