package org.linketinder.Model

class EmpresaModel extends Pessoa {

    String cnpj

    EmpresaModel(String nome, String cnpj, String email, String cep, int pais) {
        super(nome, email, cep, pais)
        this.cnpj = cnpj
    }

    EmpresaModel(){
    }

    @Override
    String toString() {
        return "Empresa{" +
                "nome='" + nome + '\'' +
                ", E-mail='" + email + '\'' +
                ", CEP='" + cep + '\'' +
                ", País='" + pais + '\'' +
                '}';

    }

}