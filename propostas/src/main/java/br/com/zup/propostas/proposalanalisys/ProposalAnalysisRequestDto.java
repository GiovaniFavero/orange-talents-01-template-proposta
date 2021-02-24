package br.com.zup.propostas.proposalanalisys;

import br.com.zup.propostas.proposalregistration.Proposal;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposalAnalysisRequestDto {

    @JsonProperty("documento")
    private String document;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("idProposta")
    private Long proposalId;

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public Long getProposalId() {
        return proposalId;
    }

    public ProposalAnalysisRequestDto(Proposal proposal) {
        this.document = proposal.getDocument();
        this.name = proposal.getName();
        this.proposalId = proposal.getId();
    }
}
