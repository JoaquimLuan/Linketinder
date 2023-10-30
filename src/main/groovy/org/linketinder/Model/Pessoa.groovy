package org.linketinder.Model

abstract class Pessoa {
    String nome
    String email
    String cep
    String pais

    Pessoa(String nome, String email, String cep, String pais) {
        this.nome = nome
        this.email = email
        this.cep = cep
        this.pais = pais
    }

    Pessoa(){
    }

}