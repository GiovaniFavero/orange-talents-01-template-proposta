package br.com.zup.propostas.travelwarningregistration;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDate;

@FeignClient(url = "${proposal.creditcard.url}", name = "travelWarningClient")
public interface NotifyTravelWarningRequestClient {

    @PostMapping("${proposal.creditcard.endpoints.travelWarning}")
    NotifyTravelWarningResponseDto notifyTravelWarning (NotifyTravelWarningRequestDto request, @PathVariable String id);

    class NotifyTravelWarningResponseDto {

        @JsonProperty("resultado")
        private String result;

        public String getResult() {
            return result;
        }

        @JsonCreator
        public NotifyTravelWarningResponseDto(@JsonProperty("resultado") String result) {
            this.result = result;
        }
    }

    class NotifyTravelWarningRequestDto {

        @JsonProperty("destino")
        private String destination;
        @JsonProperty("validoAte")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
        private LocalDate travelEndDate;

        public NotifyTravelWarningRequestDto(String destination, LocalDate travelEndDate) {
            this.destination = destination;
            this.travelEndDate = travelEndDate;
        }
    }

}
