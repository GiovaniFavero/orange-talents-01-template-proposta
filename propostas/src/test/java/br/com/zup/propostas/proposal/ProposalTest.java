package br.com.zup.propostas.proposal;

import br.com.zup.propostas.proposalregistration.Proposal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ProposalTest {

    @DisplayName("Proposal salary must be greater than 0")
    @ParameterizedTest
    @MethodSource("testOne")
    public void mustAllowCreateProposalWithPositiveSalary(BigDecimal salary){
        new Proposal("09030442930", "email@email.com", "Carlos da Silva", "São Paulo - SP", salary);
    }

    static Stream<Arguments> testOne(){
        return Stream.of(
                Arguments.of(new BigDecimal(1)),
                Arguments.of(new BigDecimal(10))
        );
    }

    @DisplayName("Proposal salary must be greater than 0")
    @ParameterizedTest
    @MethodSource("testTwo")
    public void mustAllowNotCreateProposalWithNegativeSalary(BigDecimal salary){
        assertThrows(IllegalArgumentException.class, () -> {
            new Proposal("09030442930", "email@email.com", "Carlos da Silva", "São Paulo - SP", salary);
        });

    }

    static Stream<Arguments> testTwo(){
        return Stream.of(
                Arguments.of(new BigDecimal(0)),
                Arguments.of(new BigDecimal(-10))
        );
    }

}