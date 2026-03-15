package com.protitipo.vendas.dto;

import java.time.LocalDate;

public record CustomerRequest(
    String name,
    LocalDate birthDate,
    String document,
    AddressRequest address,
    String estadoCivil
) {
}
