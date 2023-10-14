package org.example.usuarios

class Vaga {

    String nome
    String descricao
    double salario
    String empresa

    Vaga(String nome, String descricao, double salario, String empresa) {
        this.nome = nome
        this.descricao = descricao
        this.salario = salario
        this.empresa = empresa
    }

    static void adicionarVaga(vagas, scanner) {

        String nome = Regex.validarEntrada(Regex.regexNome, "Nome da vaga: ", scanner)
        String descricao = Regex.validarEntrada(Regex.regexDrescricao, "Descrição da vaga: ", scanner)
        double salario = Regex.validarEntrada(Regex.regexSalario, "Salário: ", scanner) as double
        String empresa = Regex.validarEntrada(Regex.regexNome, "Empresa: ", scanner)

        Vaga vaga = new Vaga(nome, descricao, salario, empresa);
        vagas.add(vaga)
    }

}
