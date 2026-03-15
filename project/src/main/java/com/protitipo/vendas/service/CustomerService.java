package com.protitipo.vendas.service;

import com.protitipo.vendas.domain.Address;
import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import com.protitipo.vendas.exception.NotFoundException;
import com.protitipo.vendas.exception.ValidationException;
import com.protitipo.vendas.repository.CustomerRepository;
import java.util.List;
import java.util.UUID;

public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(final CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(
        final String name,
        final java.time.LocalDate birthDate,
        final String document,
        final Address address,
        final MaritalStatus maritalStatus
    ) {
        final String normalizedDocument = document == null ? null : document.replaceAll("\\D", "");
        if (normalizedDocument != null && customerRepository.findByDocument(normalizedDocument).isPresent()) {
            throw new ValidationException("Customer with this document already exists");
        }
        final Customer customer = Customer.create(name, birthDate, normalizedDocument, address, maritalStatus);
        return customerRepository.save(customer);
    }

    public Customer updateCustomer(
        final UUID id,
        final String name,
        final java.time.LocalDate birthDate,
        final String document,
        final Address address,
        final MaritalStatus maritalStatus
    ) {
        final Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
        final String normalizedDocument = document == null ? null : document.replaceAll("\\D", "");
        customerRepository.findByDocument(normalizedDocument)
            .filter(found -> !found.getId().equals(id))
            .ifPresent(found -> {
                throw new ValidationException("Customer with this document already exists");
            });

        final Customer updatedCustomer = existingCustomer.update(name, birthDate, normalizedDocument, address, maritalStatus);
        return customerRepository.save(updatedCustomer);
    }

    public Customer getCustomer(final UUID id) {
        return customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
    }

    public List<Customer> listCustomers(final MaritalStatus maritalStatus) {
        return customerRepository.findAll(maritalStatus);
    }

    public void deleteCustomer(final UUID id) {
        final Customer existingCustomer = customerRepository.findById(id)
            .orElseThrow(() -> new NotFoundException("Customer not found"));
        customerRepository.deleteById(existingCustomer.getId());
    }
}
