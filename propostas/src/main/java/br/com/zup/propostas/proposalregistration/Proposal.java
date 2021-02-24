package br.com.zup.propostas.proposalregistration;

import br.com.zup.propostas.creditcardrequest.CreditCard;
import br.com.zup.propostas.shared.config.security.CustomEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Proposal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    @Column(unique = true)
    private String document;
    @NotBlank
    @Email
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    @Positive
    private BigDecimal salary;
    @Enumerated(EnumType.STRING)
    private ProposalState state;
    @OneToOne(cascade = CascadeType.ALL)
    private CreditCard creditCard;
    @Column(nullable = true)
    private boolean creditCardBlocked;
    @Transient
    @Value("${proposal.encryptors.document.secretKey}")
    private String secret;

    @Deprecated
    public Proposal() {
    }

    public Proposal(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @NotNull @Positive BigDecimal salary) {
        Assert.hasLength(document, "[Proposal] Document must not be blank!");
        Assert.hasLength(email, "[Proposal] Email must not be blank!");
        Assert.hasLength(name, "[Proposal] Name must not be blank!");
        Assert.hasLength(address, "[Proposal] Address must not be blank!");
        Assert.notNull(salary, "[Proposal] Salary must not be null!");
        Assert.isTrue(salary.intValue() > 0, "[Proposal] Salary must be greater than 0!");

        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public void updateCreditCard(CreditCard creditCard) {
        Assert.notNull(creditCard, "[Proposal] Credit card must not be null!");
        this.updateStatus(ProposalState.ELEGIBLE_WITH_ATTACHED_CARD);
        this.creditCard = creditCard;
    }

    public Long getId() {
        return id;
    }

    public String getDocument() {
        return CustomEncryptor.getInstance().decrypt(document);
    }

    public String getName() {
        return name;
    }

    public void updateStatus(ProposalState state) {
        this.state = state;
    }

    public ProposalState getState() {
        return state;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public boolean isCreditCardBlocked() {
        return this.creditCardBlocked;
    }

    public void blockCreditCard() {
        this.creditCardBlocked = true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Proposal proposal = (Proposal) o;
        return document.equals(proposal.document);
    }

    @Override
    public int hashCode() {
        return Objects.hash(document);
    }
}
