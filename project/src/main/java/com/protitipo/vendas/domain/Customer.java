package com.protitipo.vendas.domain;

import com.protitipo.vendas.exception.ValidationException;
import java.time.LocalDate;
import java.util.UUID;

public final class Customer {
    private final UUID id;
    private final String name;
    private final LocalDate birthDate;
    private final String document;
    private final Address address;
    private final MaritalStatus maritalStatus;

    private Customer(
        final UUID id,
        final String name,
        final LocalDate birthDate,
        final String document,
        final Address address,
        final MaritalStatus maritalStatus
    ) {
        this.id = id;
        this.name = validateName(name);
        this.birthDate = requireNonNull(birthDate, "birthDate");
        this.document = validateDocument(document);
        this.address = requireNonNull(address, "address");
        this.maritalStatus = requireNonNull(maritalStatus, "estadoCivil");
    }

    public static Customer create(
        final String name,
        final LocalDate birthDate,
        final String document,
        final Address address,
        final MaritalStatus maritalStatus
    ) {
        return new Customer(UUID.randomUUID(), name, birthDate, document, address, maritalStatus);
    }

    public Customer update(
        final String name,
        final LocalDate birthDate,
        final String document,
        final Address address,
        final MaritalStatus maritalStatus
    ) {
        return new Customer(id, name, birthDate, document, address, maritalStatus);
    }

    private static String validateName(final String value) {
        final String text = requireText(value, "name");
        if (text.length() > 60) {
            throw new ValidationException("Field 'name' must have at most 60 characters");
        }
        return text;
    }

    private static String validateDocument(final String value) {
        final String text = requireText(value, "document").replaceAll("\\D", "");
        if (!DocumentValidator.isValidCpfOrCnpj(text)) {
            throw new ValidationException("Field 'document' must be a valid CPF or CNPJ");
        }
        return text;
    }

    private static String requireText(final String value, final String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Field '%s' is required".formatted(field));
        }
        return value.trim();
    }

    private static <T> T requireNonNull(final T value, final String field) {
        if (value == null) {
            throw new ValidationException("Field '%s' is required".formatted(field));
        }
        return value;
    }

    public UUID getId() { return id; }
    public String getName() { return name; }
    public LocalDate getBirthDate() { return birthDate; }
    public String getDocument() { return document; }
    public Address getAddress() { return address; }
    public MaritalStatus getMaritalStatus() { return maritalStatus; }
}
