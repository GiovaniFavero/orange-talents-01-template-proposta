package br.com.zup.propostas.proposalanalisys;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProposalAnalysisResponseDto {

    @JsonProperty("documento")
    private String document;
    @JsonProperty("nome")
    private String name;
    @JsonProperty("resultadoSolicitacao")
    private String requestResponse;
    @JsonProperty("idProposta")
    private String proposalId;

    public String getDocument() {
        return document;
    }

    public String getName() {
        return name;
    }

    public String getRequestResponse() {
        return requestResponse;
    }

    public String getProposalId() {
        return proposalId;
    }
}
