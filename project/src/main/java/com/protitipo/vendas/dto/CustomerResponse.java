package com.protitipo.vendas.dto;

import java.time.LocalDate;
import java.util.UUID;

public record CustomerResponse(
    UUID id,
    String name,
    LocalDate birthDate,
    String document,
    AddressResponse address,
    String estadoCivil
) {
}
