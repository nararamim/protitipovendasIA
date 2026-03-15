package com.protitipo.vendas.domain;

import java.util.Objects;

public class Address {
    private final String street;
    private final String number;
    private final String complement;
    private final String district;
    private final String city;
    private final String state;

    public Address(final String street,
                   final String number,
                   final String complement,
                   final String district,
                   final String city,
                   final String state) {
        this.street = requireNotBlank(street, "Rua é obrigatória");
        this.number = requireNotBlank(number, "Número é obrigatório");
        this.complement = complement == null ? "" : complement.trim();
        this.district = requireNotBlank(district, "Bairro é obrigatório");
        this.city = requireNotBlank(city, "Cidade é obrigatória");
        this.state = requireNotBlank(state, "Estado é obrigatório");
    }

    private static String requireNotBlank(final String value, final String message) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(message);
        }
        return value.trim();
    }

    public String getStreet() {
        return street;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }

    public String getDistrict() {
        return district;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Address address)) {
            return false;
        }
        return Objects.equals(street, address.street)
                && Objects.equals(number, address.number)
                && Objects.equals(complement, address.complement)
                && Objects.equals(district, address.district)
                && Objects.equals(city, address.city)
                && Objects.equals(state, address.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(street, number, complement, district, city, state);
    }
}
