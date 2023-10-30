package org.linketinder.Model

enum RegexPattern {

    NOME(/^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/),
    CNPJ(/^\d{14}$/),
    EMAIL('^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'),
    CEP(/^\d{8}$/),
    PAIS(/^[A-Za-z\s]{1,35}$/),
    NASCIMENTO(/^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$/),
    CPF(/^\d{11}$/),
    DESCRICAO(/^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,50}$/),
    SALARIO(/^\d+(\.\d{1,2})?$/)

    private String pattern

    RegexPattern(String pattern) {
        this.pattern = pattern
    }

    String getPattern() {
        return pattern
    }
}