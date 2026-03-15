package com.protitipo.vendas.domain;

import com.protitipo.vendas.exception.ValidationException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest {

    @Test
    void shouldCreateCustomerWithValidMaritalStatus() {
        final Customer customer = Customer.create(
            "Maria Silva",
            LocalDate.of(1990, 1, 10),
            "11144477735",
            validAddress(),
            MaritalStatus.CASADO
        );

        assertEquals(MaritalStatus.CASADO, customer.getMaritalStatus());
    }

    @Test
    void shouldFailWhenMaritalStatusIsNull() {
        final ValidationException exception = assertThrows(ValidationException.class, () -> Customer.create(
            "Maria Silva",
            LocalDate.of(1990, 1, 10),
            "11144477735",
            validAddress(),
            null
        ));

        assertTrue(exception.getMessage().contains("estadoCivil"));
    }

    private Address validAddress() {
        return new Address("Rua A", "123", "", "Centro", "São Paulo", "SP");
    }
}
