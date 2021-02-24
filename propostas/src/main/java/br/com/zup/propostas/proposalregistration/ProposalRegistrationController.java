package br.com.zup.propostas.proposalregistration;

import br.com.zup.propostas.proposalanalisys.ProposalAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api/proposals")
public class ProposalRegistrationController {

    @Autowired
    private CheckCpfCnpjValidator checkCpfCnpjValidator;
    @Autowired
    private ProposalRepository proposalRepository;
    @Autowired
    private ProposalAnalysisService proposalAnalysisService;

    @InitBinder
    public void init (WebDataBinder binder) {
        binder.addValidators(checkCpfCnpjValidator);
    }

    @PostMapping
    public ResponseEntity create (@RequestBody @Valid NewProposalRequestDto request, UriComponentsBuilder uriBuilder) throws InterruptedException {
        Proposal proposal = request.toModel();
        proposalRepository.save(proposal);

        proposalAnalysisService.processProposalAnalysis(proposal);

        proposalRepository.save(proposal);

        URI uri = uriBuilder.path("/api/proposal/{id}").buildAndExpand(proposal.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
