package br.com.zup.propostas.proposalregistration;

import org.hibernate.LockOptions;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.QueryHints;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.List;
import java.util.Optional;

public interface ProposalRepository extends JpaRepository<Proposal, Long> {

//    @Lock(LockModeType.PESSIMISTIC_WRITE)
//    @QueryHints({
//            @QueryHint(name = "javax.persistence.lock.timeout", value = (LockOptions.SKIP_LOCKED + ""))
//    })
    List<Proposal> findTop100ByStateOrderByIdAsc(ProposalState state);

    Optional<Proposal> findByCreditCardId(String id);

}
