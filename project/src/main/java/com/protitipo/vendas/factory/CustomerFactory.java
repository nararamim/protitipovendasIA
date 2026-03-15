package com.protitipo.vendas.factory;

import com.protitipo.vendas.domain.Address;
import com.protitipo.vendas.domain.Customer;
import com.protitipo.vendas.domain.Document;

import java.time.LocalDate;

public final class CustomerFactory {
    private CustomerFactory() {
    }

    public static Customer create(final String id,
                                  final String name,
                                  final LocalDate birthDate,
                                  final String documentValue,
                                  final String street,
                                  final String number,
                                  final String complement,
                                  final String district,
                                  final String city,
                                  final String state) {
        final Document document = new Document(documentValue);
        final Address address = new Address(street, number, complement, district, city, state);

        return new Customer(id, name, birthDate, document, address);
    }
}
