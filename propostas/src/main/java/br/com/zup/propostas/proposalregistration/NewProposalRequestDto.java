package br.com.zup.propostas.proposalregistration;

import br.com.zup.propostas.shared.config.security.CustomEncryptor;
import br.com.zup.propostas.shared.config.validation.annotations.UniqueValue;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CNPJValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.br.CPFValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

public class NewProposalRequestDto {

    @NotBlank
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

    @UniqueValue(domainClass = Proposal.class, fieldName = "document")
    public String getEncryptedDocument() {
        return CustomEncryptor.getInstance().encrypt(this.document);
    }


    public NewProposalRequestDto(@NotBlank String document, @NotBlank @Email String email, @NotBlank String name, @NotBlank String address, @NotNull @Positive BigDecimal salary) {
        this.document = document;
        this.email = email;
        this.name = name;
        this.address = address;
        this.salary = salary;
    }

    public String getDocument() {
        return document;
    }

    public boolean isValidDocument() {
        Assert.hasLength(this.document, "The document can't be blank!");

        CPFValidator cpfValidator = new CPFValidator();
        cpfValidator.initialize(null);

        CNPJValidator cnpjValidator = new CNPJValidator();
        cnpjValidator.initialize(null);

        return cpfValidator.isValid(this.document, null) || cnpjValidator.isValid(this.document, null);
    }

    public boolean isUniqueDocument(EntityManager entityManager) {
        List<?> list = entityManager.createQuery("select 1 from Proposal p where p.document = :document")
                                    .setParameter("document", this.document)
                                    .getResultList();
        return list.isEmpty();
    }

    public Proposal toModel() {
        return new Proposal(CustomEncryptor.getInstance().encrypt(document), this.email, this.name, this.address, this.salary);
    }
}
