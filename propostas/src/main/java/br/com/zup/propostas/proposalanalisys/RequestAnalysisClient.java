package br.com.zup.propostas.proposalanalisys;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(url = "${proposal.analysis.url}", name = "request")
public interface RequestAnalysisClient {

    @PostMapping
    ProposalAnalysisResponseDto sendRequestAnalysis (ProposalAnalysisRequestDto request);

}