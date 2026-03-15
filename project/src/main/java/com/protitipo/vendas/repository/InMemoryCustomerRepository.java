package com.protitipo.vendas.repository;

import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.MaritalStatus;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCustomerRepository implements CustomerRepository {
    private final Map<UUID, Customer> customers = new ConcurrentHashMap<>();

    @Override
    public Customer save(final Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(final UUID id) {
        return Optional.ofNullable(customers.get(id));
    }

    @Override
    public Optional<Customer> findByDocument(final String document) {
        return customers.values().stream()
            .filter(customer -> customer.getDocument().equals(document))
            .findFirst();
    }

    @Override
    public List<Customer> findAll(final MaritalStatus maritalStatus) {
        return customers.values().stream()
            .filter(customer -> maritalStatus == null || customer.getMaritalStatus() == maritalStatus)
            .sorted(Comparator.comparing(Customer::getName))
            .collect(ArrayList::new, ArrayList::add, ArrayList::addAll);
    }

    @Override
    public void deleteById(final UUID id) {
        customers.remove(id);
    }
}
