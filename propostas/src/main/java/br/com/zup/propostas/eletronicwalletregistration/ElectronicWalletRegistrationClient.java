package br.com.zup.propostas.eletronicwalletregistration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${proposal.creditcard.url}", name = "electronicWalletClient")
public interface ElectronicWalletRegistrationClient {

    @PostMapping("${proposal.creditcard.endpoints.electronicWallet}")
    NotifyElectronicWalletResponseDto notifyElectronicWallet (NotifyElectronicWalletRequestDto request, @PathVariable("id") String creditCardId);

    class NotifyElectronicWalletRequestDto {

        @JsonProperty("email")
        private String email;
        @JsonProperty("carteira")
        private WalletCompany wallet;

        public NotifyElectronicWalletRequestDto(NewElectronicWalletRequestDto request) {
            this.email = request.getEmail();
            this.wallet = WalletCompany.valueOf(request.getWallet());
        }
    }

    class NotifyElectronicWalletResponseDto {

        @JsonProperty("resultado")
        private String result;
        @JsonProperty("id")
        private String id;

        @JsonCreator
        public NotifyElectronicWalletResponseDto(@JsonProperty("resultado") String result, @JsonProperty("id") String id) {
            this.result = result;
            this.id = id;
        }

        public String getResult() {
            return result;
        }

        public String getId() {
            return id;
        }
    }

}
