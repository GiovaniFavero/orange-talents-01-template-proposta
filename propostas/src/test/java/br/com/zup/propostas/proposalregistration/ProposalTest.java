package br.com.zup.propostas.proposalregistration;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ProposalTest {

    @DisplayName("Proposal must have salary greater than zero")
    @ParameterizedTest
    @MethodSource("testOne")
    void mustNotAllowToCreateProposalWithNonPositiveSalary(BigDecimal salary) {
        assertThrows(IllegalArgumentException.class, () -> {
            new Proposal("969.388.740-93", "email@email.com", "Carlos", "Address", salary);
        });
    }

    static Stream<Arguments> testOne() {
        return Stream.of(
                Arguments.of(new BigDecimal(0)),
                Arguments.of(new BigDecimal(-10))
        );
    }

    @DisplayName("Proposal must have salary greater than zero")
    @ParameterizedTest
    @MethodSource("testTwo")
    void mustAllowToCreateProposalWithPositiveSalary(BigDecimal salary) {
        new Proposal("969.388.740-93", "email@email.com", "Carlos", "Address", salary);
    }

    static Stream<Arguments> testTwo() {
        return Stream.of(
                Arguments.of(new BigDecimal(1)),
                Arguments.of(new BigDecimal(10))
        );
    }

}