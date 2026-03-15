package com.protitipo.vendas;

import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.DocumentType;
import com.protitipo.vendas.factory.CustomerFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CustomerFactoryTest {

    @Test
    void shouldCreateCustomerWithValidCpf() {
        final Customer customer = CustomerFactory.create(
                "C-001",
                "Maria da Silva",
                LocalDate.of(1990, 5, 12),
                "529.982.247-25",
                "Rua A",
                "100",
                "Apto 101",
                "Centro",
                "São Paulo",
                "SP"
        );

        assertEquals("Maria da Silva", customer.getName());
        assertEquals(DocumentType.CPF, customer.getDocument().getType());
        assertEquals("52998224725", customer.getDocument().getValue());
        assertEquals("Rua A", customer.getAddress().getStreet());
    }

    @Test
    void shouldCreateCustomerWithValidCnpj() {
        final Customer customer = CustomerFactory.create(
                "C-002",
                "Empresa XPTO",
                LocalDate.of(2000, 1, 1),
                "04.252.011/0001-10",
                "Av. Brasil",
                "500",
                "Sala 3",
                "Jardins",
                "São Paulo",
                "SP"
        );

        assertEquals(DocumentType.CNPJ, customer.getDocument().getType());
        assertEquals("04252011000110", customer.getDocument().getValue());
    }

    @Test
    void shouldRejectNameLongerThan60Characters() {
        final String longName = "A".repeat(61);

        assertThrows(IllegalArgumentException.class, () -> CustomerFactory.create(
                "C-003",
                longName,
                LocalDate.of(1991, 8, 21),
                "52998224725",
                "Rua B",
                "200",
                "",
                "Bairro B",
                "Rio de Janeiro",
                "RJ"
        ));
    }

    @Test
    void shouldRejectInvalidCpf() {
        assertThrows(IllegalArgumentException.class, () -> CustomerFactory.create(
                "C-004",
                "João da Silva",
                LocalDate.of(1988, 3, 20),
                "111.111.111-11",
                "Rua C",
                "300",
                "",
                "Centro",
                "Belo Horizonte",
                "MG"
        ));
    }

    @Test
    void shouldRejectInvalidCnpj() {
        assertThrows(IllegalArgumentException.class, () -> CustomerFactory.create(
                "C-005",
                "Empresa Inválida",
                LocalDate.of(2010, 10, 10),
                "11.111.111/1111-11",
                "Rua D",
                "400",
                "",
                "Centro",
                "Curitiba",
                "PR"
        ));
    }

    @Test
    void shouldRejectBlankAddressFields() {
        assertThrows(IllegalArgumentException.class, () -> CustomerFactory.create(
                "C-006",
                "Carlos Eduardo",
                LocalDate.of(1995, 11, 30),
                "52998224725",
                "",
                "10",
                "",
                "Centro",
                "Recife",
                "PE"
        ));
    }
}
