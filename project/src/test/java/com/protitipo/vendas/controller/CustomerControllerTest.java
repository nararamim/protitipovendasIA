package com.protitipo.vendas.controller;

import com.protitipo.vendas.dto.AddressRequest;
import com.protitipo.vendas.dto.CustomerRequest;
import com.protitipo.vendas.repository.InMemoryCustomerRepository;
import com.protitipo.vendas.service.CustomerService;
import java.time.LocalDate;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerControllerTest {

    @Test
    void shouldReturn400WhenEstadoCivilIsMissing() {
        final CustomerController controller = new CustomerController(new CustomerService(new InMemoryCustomerRepository()));

        final ApiResponse<?> response = controller.create(new CustomerRequest(
            "Maria",
            LocalDate.of(1990, 5, 20),
            "11144477735",
            validAddress(),
            null
        ));

        assertEquals(400, response.status());
        assertTrue(response.error().contains("estadoCivil"));
    }

    @Test
    void shouldReturn400WhenEstadoCivilIsInvalid() {
        final CustomerController controller = new CustomerController(new CustomerService(new InMemoryCustomerRepository()));

        final ApiResponse<?> response = controller.create(new CustomerRequest(
            "Maria",
            LocalDate.of(1990, 5, 20),
            "11144477735",
            validAddress(),
            "CASADA"
        ));

        assertEquals(400, response.status());
    }

    @Test
    void shouldCreateAndExposeEstadoCivilOnGet() {
        final CustomerController controller = new CustomerController(new CustomerService(new InMemoryCustomerRepository()));

        final ApiResponse<?> createResponse = controller.create(new CustomerRequest(
            "Maria",
            LocalDate.of(1990, 5, 20),
            "11144477735",
            validAddress(),
            "VIUVO"
        ));

        assertEquals(201, createResponse.status());
        final UUID id = ((com.protitipo.vendas.dto.CustomerResponse) createResponse.data()).id();
        final ApiResponse<?> getResponse = controller.getById(id);
        assertEquals(200, getResponse.status());
        assertEquals("VIUVO", ((com.protitipo.vendas.dto.CustomerResponse) getResponse.data()).estadoCivil());
    }

    @Test
    void shouldFilterByEstadoCivil() {
        final CustomerController controller = new CustomerController(new CustomerService(new InMemoryCustomerRepository()));
        controller.create(new CustomerRequest("A", LocalDate.of(1990, 1, 1), "11144477735", validAddress(), "SOLTEIRO"));
        controller.create(new CustomerRequest("B", LocalDate.of(1990, 1, 1), "52998224725", validAddress(), "CASADO"));

        final ApiResponse<?> response = controller.list("CASADO");

        assertEquals(200, response.status());
        assertEquals(1, ((java.util.List<?>) response.data()).size());
    }

    private AddressRequest validAddress() {
        return new AddressRequest("Rua X", "100", "", "Centro", "Recife", "PE");
    }
}
