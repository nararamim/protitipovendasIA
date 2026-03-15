package com.protitipo.vendas.repository;

import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerRepository {
    Customer save(final Customer customer);
    Optional<Customer> findById(final UUID id);
    Optional<Customer> findByDocument(final String document);
    List<Customer> findAll(final MaritalStatus maritalStatus);
    void deleteById(final UUID id);
}
