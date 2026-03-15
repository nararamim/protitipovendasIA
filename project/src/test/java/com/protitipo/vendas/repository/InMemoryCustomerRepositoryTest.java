package com.protitipo.vendas.repository;

import com.protitipo.vendas.domain.Address;
import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InMemoryCustomerRepositoryTest {

    @Test
    void shouldPersistAndReadMaritalStatus() {
        final InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        final Customer customer = Customer.create(
            "João Souza",
            LocalDate.of(1989, 5, 2),
            "52998224725",
            new Address("Rua A", "10", "", "Centro", "Curitiba", "PR"),
            MaritalStatus.UNIAO_ESTAVEL
        );

        repository.save(customer);

        final Customer found = repository.findById(customer.getId()).orElseThrow();
        assertEquals(MaritalStatus.UNIAO_ESTAVEL, found.getMaritalStatus());
    }

    @Test
    void shouldFilterByMaritalStatus() {
        final InMemoryCustomerRepository repository = new InMemoryCustomerRepository();
        repository.save(Customer.create("Ana", LocalDate.of(1992, 2, 2), "11144477735", addr(), MaritalStatus.SOLTEIRO));
        repository.save(Customer.create("Bruno", LocalDate.of(1993, 3, 3), "93541134780", addr(), MaritalStatus.CASADO));

        final List<Customer> filtered = repository.findAll(MaritalStatus.CASADO);

        assertEquals(1, filtered.size());
        assertEquals(MaritalStatus.CASADO, filtered.get(0).getMaritalStatus());
    }

    private Address addr() {
        return new Address("Rua B", "2", "", "Bairro", "Cidade", "ST");
    }
}
