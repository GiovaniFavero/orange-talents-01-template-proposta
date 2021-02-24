package br.com.zup.propostas.creditcardrequest;

import br.com.zup.propostas.proposalregistration.Proposal;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url = "${proposal.creditcard.url}", name = "creditcard")
public interface RequestCreditCardClient {

    @PostMapping
    void requestForCreditCard(CreditCardRequestDto creditCard);

    @GetMapping
    CreditCardResponseDto checkGeneratedCreditCard(@RequestParam("idProposta") Long proposalId);

    class CreditCardRequestDto {

        @JsonProperty("documento")
        private String document;
        @JsonProperty("nome")
        private String name;
        @JsonProperty("idProposta")
        private Long proposalId;

        public CreditCardRequestDto(Proposal proposal) {
            this.document = proposal.getDocument();
            this.name = proposal.getName();
            this.proposalId = proposal.getId();
        }
    }



}
