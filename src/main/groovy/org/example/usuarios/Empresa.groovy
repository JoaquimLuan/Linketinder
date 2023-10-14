package org.example.usuarios

class Empresa extends Pessoa {

    long cnpj

    Empresa(String nome, long cnpj, String email, int cep, String pais) {
        super(nome, email, cep, pais)
        this.cnpj = cnpj
    }

    static void adicionarEmpresas(empresas, scanner) {

        String nome = Regex.validarEntrada(Regex.regexNome, "Nome: ", scanner)
        long cnpj = Regex.validarEntrada(Regex.regexCnpj, "CNPJ: ", scanner) as long
        String email = Regex.validarEntrada(Regex.regexEmail, "Email: ", scanner)
        int cep = Regex.validarEntrada(Regex.regexCep, "CEP: ", scanner) as int
        String pais = Regex.validarEntrada(Regex.regexPais, "País: ", scanner)

        Empresa empresa = new Empresa(nome, cnpj, email, cep, pais);
        empresas.add(empresa)
    }

}