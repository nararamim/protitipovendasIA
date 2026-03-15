package com.protitipo.vendas.domain;

import java.time.LocalDate;
import java.util.Objects;

public class Customer {
    private final String id;
    private final String name;
    private final LocalDate birthDate;
    private final Document document;
    private final Address address;

    public Customer(final String id,
             final String name,
             final LocalDate birthDate,
             final Document document,
             final Address address) {
        this.id = requireNotBlank(id, "Identificador de cliente é obrigatório");
        this.name = validateName(name);
        this.birthDate = Objects.requireNonNull(birthDate, "Data de nascimento é obrigatória");
        this.document = Objects.requireNonNull(document, "Documento é obrigatório");
        this.address = Objects.requireNonNull(address, "Endereço é obrigatório");
    }

    private static String requireNotBlank(final String value, final String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    private static String validateName(final String name) {
        final String trimmed = requireNotBlank(name, "Nome é obrigatório");
        if (trimmed.length() > 60) {
            throw new IllegalArgumentException("Nome deve ter no máximo 60 caracteres");
        }
        return trimmed;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public Document getDocument() {
        return document;
    }

    public Address getAddress() {
        return address;
    }
}
