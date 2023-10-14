package org.example.usuarios

abstract class Pessoa {
    String nome
    String email
    int cep
    String pais

    Pessoa(String nome, String email, int cep, String pais) {
        this.nome = nome
        this.email = email
        this.cep = cep
        this.pais = pais
    }

}