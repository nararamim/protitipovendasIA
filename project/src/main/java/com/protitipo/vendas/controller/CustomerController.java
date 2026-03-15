package com.protitipo.vendas.controller;

import com.protitipo.vendas.domain.Address;
import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import com.protitipo.vendas.dto.AddressRequest;
import com.protitipo.vendas.dto.AddressResponse;
import com.protitipo.vendas.dto.CustomerRequest;
import com.protitipo.vendas.dto.CustomerResponse;
import com.protitipo.vendas.exception.NotFoundException;
import com.protitipo.vendas.exception.ValidationException;
import com.protitipo.vendas.service.CustomerService;
import java.util.List;
import java.util.UUID;

public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(final CustomerService customerService) {
        this.customerService = customerService;
    }

    public ApiResponse<CustomerResponse> create(final CustomerRequest request) {
        try {
            final Customer created = customerService.createCustomer(
                request.name(),
                request.birthDate(),
                request.document(),
                toAddress(request.address()),
                parseMaritalStatus(request.estadoCivil())
            );
            return ApiResponse.created(toResponse(created));
        } catch (final ValidationException exception) {
            return ApiResponse.badRequest(exception.getMessage());
        }
    }

    public ApiResponse<CustomerResponse> update(final UUID id, final CustomerRequest request) {
        try {
            final Customer updated = customerService.updateCustomer(
                id,
                request.name(),
                request.birthDate(),
                request.document(),
                toAddress(request.address()),
                parseMaritalStatus(request.estadoCivil())
            );
            return ApiResponse.ok(toResponse(updated));
        } catch (final ValidationException exception) {
            return ApiResponse.badRequest(exception.getMessage());
        } catch (final NotFoundException exception) {
            return ApiResponse.notFound(exception.getMessage());
        }
    }

    public ApiResponse<CustomerResponse> getById(final UUID id) {
        try {
            return ApiResponse.ok(toResponse(customerService.getCustomer(id)));
        } catch (final NotFoundException exception) {
            return ApiResponse.notFound(exception.getMessage());
        }
    }

    public ApiResponse<List<CustomerResponse>> list(final String estadoCivil) {
        try {
            final MaritalStatus maritalStatus = estadoCivil == null ? null : parseMaritalStatus(estadoCivil);
            return ApiResponse.ok(customerService.listCustomers(maritalStatus).stream().map(this::toResponse).toList());
        } catch (final ValidationException exception) {
            return ApiResponse.badRequest(exception.getMessage());
        }
    }

    public ApiResponse<Void> delete(final UUID id) {
        try {
            customerService.deleteCustomer(id);
            return ApiResponse.noContent();
        } catch (final NotFoundException exception) {
            return ApiResponse.notFound(exception.getMessage());
        }
    }

    private MaritalStatus parseMaritalStatus(final String estadoCivil) {
        if (estadoCivil == null || estadoCivil.trim().isEmpty()) {
            throw new ValidationException("Field 'estadoCivil' is required");
        }
        try {
            return MaritalStatus.valueOf(estadoCivil.trim());
        } catch (final IllegalArgumentException exception) {
            throw new ValidationException("Field 'estadoCivil' has invalid value");
        }
    }

    private Address toAddress(final AddressRequest request) {
        if (request == null) {
            throw new ValidationException("Field 'address' is required");
        }
        return new Address(
            request.street(),
            request.number(),
            request.complement(),
            request.neighborhood(),
            request.city(),
            request.state()
        );
    }

    private CustomerResponse toResponse(final Customer customer) {
        return new CustomerResponse(
            customer.getId(),
            customer.getName(),
            customer.getBirthDate(),
            customer.getDocument(),
            new AddressResponse(
                customer.getAddress().getStreet(),
                customer.getAddress().getNumber(),
                customer.getAddress().getComplement(),
                customer.getAddress().getNeighborhood(),
                customer.getAddress().getCity(),
                customer.getAddress().getState()
            ),
            customer.getMaritalStatus().name()
        );
    }
}
