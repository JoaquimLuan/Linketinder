package org.example.usuarios

import java.text.ParseException
import java.text.SimpleDateFormat

class Candidato extends Pessoa {

    String sobrenome
    Date dataNascimento
    long cpf

    Candidato(String nome, String sobrenome, Date dataNascimento, String email, long cpf, int cep, String pais) {
        super(nome, email, cep,  pais)
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.cpf = cpf
    }

    static void adicionarCandidato(List<Candidato> candidatos, Scanner scanner) {

        String nome = Regex.validarEntrada(Regex.regexNome, "Nome: ", scanner);
        String sobrenome = Regex.validarEntrada(Regex.regexSobrenome, "Sobrenome: ", scanner);
        Date dataNascimento = ValidacaoDeData.validarEntradaData(Regex.regexNascimento, "Data de nascimento (formato dd-MM-yyyy): ", scanner);
        String email = Regex.validarEntrada(Regex.regexEmail, "Email: ", scanner);
        long cpf = Long.parseLong(Regex.validarEntrada(Regex.regexCpf, "CPF: ", scanner))
        int cep = Regex.validarEntrada(Regex.regexCep, "CEP: ", scanner) as int
        String pais = Regex.validarEntrada(Regex.regexPais, "País: ", scanner);

        Candidato candidato = new Candidato(nome, sobrenome, dataNascimento, email, cpf, cep, pais);
        candidatos.add(candidato);
    }


}