package com.protitipo.vendas.domain;

import com.protitipo.vendas.exception.ValidationException;

public final class Address {
    private final String street;
    private final String number;
    private final String complement;
    private final String neighborhood;
    private final String city;
    private final String state;

    public Address(
        final String street,
        final String number,
        final String complement,
        final String neighborhood,
        final String city,
        final String state
    ) {
        this.street = requireText(street, "street");
        this.number = requireText(number, "number");
        this.complement = complement == null ? "" : complement.trim();
        this.neighborhood = requireText(neighborhood, "neighborhood");
        this.city = requireText(city, "city");
        this.state = requireText(state, "state");
    }

    private String requireText(final String value, final String field) {
        if (value == null || value.trim().isEmpty()) {
            throw new ValidationException("Field '%s' is required".formatted(field));
        }
        return value.trim();
    }

    public String getStreet() { return street; }
    public String getNumber() { return number; }
    public String getComplement() { return complement; }
    public String getNeighborhood() { return neighborhood; }
    public String getCity() { return city; }
    public String getState() { return state; }
}
