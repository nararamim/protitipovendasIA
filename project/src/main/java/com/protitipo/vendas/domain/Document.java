package com.protitipo.vendas.domain;

import java.util.Objects;

public class Document {
    private final String value;
    private final DocumentType type;

    public Document(final String value) {
        final String digits = onlyDigits(value);
        if (isValidCpf(digits)) {
            this.value = digits;
            this.type = DocumentType.CPF;
            return;
        }
        if (isValidCnpj(digits)) {
            this.value = digits;
            this.type = DocumentType.CNPJ;
            return;
        }
        throw new IllegalArgumentException("Documento inválido. Informe um CPF ou CNPJ válido.");
    }

    private static String onlyDigits(final String value) {
        if (value == null) {
            throw new IllegalArgumentException("Documento é obrigatório");
        }
        return value.replaceAll("\\D", "");
    }

    public String getValue() {
        return value;
    }

    public DocumentType getType() {
        return type;
    }

    private static boolean isValidCpf(final String cpf) {
        if (cpf.length() != 11 || allDigitsEqual(cpf)) {
            return false;
        }

        final int firstDigit = calculateCpfDigit(cpf, 9, 10);
        final int secondDigit = calculateCpfDigit(cpf, 10, 11);

        return firstDigit == Character.getNumericValue(cpf.charAt(9))
                && secondDigit == Character.getNumericValue(cpf.charAt(10));
    }

    private static int calculateCpfDigit(final String cpf, final int length, final int weightStart) {
        int sum = 0;
        int weight = weightStart;
        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * weight;
            weight--;
        }
        final int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static boolean isValidCnpj(final String cnpj) {
        if (cnpj.length() != 14 || allDigitsEqual(cnpj)) {
            return false;
        }

        final int firstDigit = calculateCnpjDigit(cnpj, 12);
        final int secondDigit = calculateCnpjDigit(cnpj, 13);

        return firstDigit == Character.getNumericValue(cnpj.charAt(12))
                && secondDigit == Character.getNumericValue(cnpj.charAt(13));
    }

    private static int calculateCnpjDigit(final String cnpj, final int length) {
        final int[] weights = length == 12
                ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
                : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};

        int sum = 0;
        for (int i = 0; i < length; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
        }

        final int remainder = sum % 11;
        return remainder < 2 ? 0 : 11 - remainder;
    }

    private static boolean allDigitsEqual(final String value) {
        final char first = value.charAt(0);
        for (int i = 1; i < value.length(); i++) {
            if (value.charAt(i) != first) {
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Document document)) {
            return false;
        }
        return Objects.equals(value, document.value) && type == document.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, type);
    }
}
