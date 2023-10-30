package org.linketinder.Model

class VagaModel {

    String nome
    String descricao
    double salario
    String empresa

    VagaModel(String nome, String descricao, double salario, String empresa) {
        this.nome = nome
        this.descricao = descricao
        this.salario = salario
        this.empresa = empresa
    }

    VagaModel(){
    }


}
