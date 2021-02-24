package br.com.zup.propostas.proposalregistration;

public class ProposalResponseDto {

    private Long id;
    private ProposalState state;
    private String creditCardId;
    private String document;

    public ProposalResponseDto(Proposal proposal) {
        this.id = proposal.getId();
        this.state = proposal.getState();
        this.creditCardId = proposal.getCreditCard() != null ? proposal.getCreditCard().getNumber() : null;
        this.document = proposal.getDocument();
    }

    public Long getId() {
        return id;
    }

    public ProposalState getState() {
        return state;
    }

    public String getCreditCardId() {
        return creditCardId;
    }

    public String getDocument() {
        return document;
    }
}
