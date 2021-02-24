package br.com.zup.propostas.proposalregistration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/proposals")
public class ProposalConsultationController {

    @Autowired
    private ProposalRepository proposalRepository;

    @GetMapping("/{id}")
    public ResponseEntity<ProposalResponseDto> getProposal (@PathVariable Long id) {
        Optional<Proposal> proposal = proposalRepository.findById(id);
        if(proposal.isPresent()) {
            return ResponseEntity.ok(new ProposalResponseDto(proposal.get()));
        }
        return ResponseEntity.notFound().build();
    }

}
