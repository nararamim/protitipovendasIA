package com.protitipo.vendas.dto;

public record AddressRequest(
    String street,
    String number,
    String complement,
    String neighborhood,
    String city,
    String state
) {
}
