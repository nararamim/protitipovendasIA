package com.protitipo.vendas.service;

import com.protitipo.vendas.domain.Address;
import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import com.protitipo.vendas.exception.NotFoundException;
import com.protitipo.vendas.exception.ValidationException;
import com.protitipo.vendas.repository.InMemoryCustomerRepository;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    @Test
    void shouldUpdateMaritalStatus() {
        final CustomerService service = new CustomerService(new InMemoryCustomerRepository());
        final Customer created = service.createCustomer("Carlos", LocalDate.of(1991, 1, 1), "11144477735", addr(), MaritalStatus.SOLTEIRO);

        final Customer updated = service.updateCustomer(created.getId(), "Carlos", LocalDate.of(1991, 1, 1), "11144477735", addr(), MaritalStatus.DIVORCIADO);

        assertEquals(MaritalStatus.DIVORCIADO, updated.getMaritalStatus());
    }

    @Test
    void shouldFailWhenCreatingWithDuplicatedDocument() {
        final CustomerService service = new CustomerService(new InMemoryCustomerRepository());
        service.createCustomer("Carlos", LocalDate.of(1991, 1, 1), "11144477735", addr(), MaritalStatus.SOLTEIRO);

        assertThrows(ValidationException.class, () ->
            service.createCustomer("Ana", LocalDate.of(1992, 2, 2), "11144477735", addr(), MaritalStatus.CASADO)
        );
    }

    @Test
    void shouldReturnNotFoundWhenUpdatingUnknownCustomer() {
        final CustomerService service = new CustomerService(new InMemoryCustomerRepository());

        assertThrows(NotFoundException.class, () ->
            service.updateCustomer(java.util.UUID.randomUUID(), "Ana", LocalDate.of(1992, 2, 2), "52998224725", addr(), MaritalStatus.CASADO)
        );
    }

    private Address addr() {
        return new Address("Rua B", "2", "", "Bairro", "Cidade", "ST");
    }
}
