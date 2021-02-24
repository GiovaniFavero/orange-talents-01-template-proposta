package br.com.zup.propostas.creditcardblock;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${proposal.creditcard.url}", name = "creditCardBlocking")
public interface NotifyCreditCardBlockingClient {

    @PostMapping("${proposal.creditcard.endpoints.notifyBlocking}")
    NotifyCreditCardBlockingResponseDto notifyCreditCardBlocking (NotifyCreditCardBlockingRequestDto request, @PathVariable String id);

    class NotifyCreditCardBlockingRequestDto {

        @JsonProperty("sistemaResponsavel")
        private String responsibleSystem;

        public NotifyCreditCardBlockingRequestDto(String responsibleSystem) {
            this.responsibleSystem = responsibleSystem;
        }
    }

    class NotifyCreditCardBlockingResponseDto {

        @JsonProperty("resultado")
        private String result;

        @JsonCreator
        public NotifyCreditCardBlockingResponseDto(@JsonProperty("resultado") String result) {
            this.result = result;
        }

        public String getResult() {
            return result;
        }
    }
}
