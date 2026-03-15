package com.protitipo.vendas.domain;

public final class DocumentValidator {
    private DocumentValidator() {
    }

    public static boolean isValidCpfOrCnpj(final String document) {
        if (document == null) {
            return false;
        }
        final String digits = document.replaceAll("\\D", "");
        return isValidCpf(digits) || isValidCnpj(digits);
    }

    private static boolean isValidCpf(final String cpf) {
        if (cpf.length() != 11 || cpf.chars().distinct().count() == 1) {
            return false;
        }
        return calculateCpfDigit(cpf, 9) == Character.getNumericValue(cpf.charAt(9))
            && calculateCpfDigit(cpf, 10) == Character.getNumericValue(cpf.charAt(10));
    }

    private static int calculateCpfDigit(final String cpf, final int position) {
        int sum = 0;
        int weight = position + 1;
        for (int i = 0; i < position; i++) {
            sum += Character.getNumericValue(cpf.charAt(i)) * (weight - i);
        }
        final int result = 11 - (sum % 11);
        return result > 9 ? 0 : result;
    }

    private static boolean isValidCnpj(final String cnpj) {
        if (cnpj.length() != 14 || cnpj.chars().distinct().count() == 1) {
            return false;
        }
        return calculateCnpjDigit(cnpj, 12) == Character.getNumericValue(cnpj.charAt(12))
            && calculateCnpjDigit(cnpj, 13) == Character.getNumericValue(cnpj.charAt(13));
    }

    private static int calculateCnpjDigit(final String cnpj, final int position) {
        final int[] weights = position == 12
            ? new int[]{5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2}
            : new int[]{6, 5, 4, 3, 2, 9, 8, 7, 6, 5, 4, 3, 2};
        int sum = 0;
        for (int i = 0; i < position; i++) {
            sum += Character.getNumericValue(cnpj.charAt(i)) * weights[i];
        }
        final int result = sum % 11;
        return result < 2 ? 0 : 11 - result;
    }
}
