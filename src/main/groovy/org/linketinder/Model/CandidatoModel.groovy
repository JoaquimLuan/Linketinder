package org.linketinder.Model

class CandidatoModel extends Pessoa {

    String sobrenome
    Date dataNascimento
    String cpf

    CandidatoModel(String nome, String sobrenome, Date dataNascimento, String email, String cpf, String cep, int pais) {
        super(nome, email, cep,  pais)
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.cpf = cpf
    }

    CandidatoModel() {
    }

    @Override
    String toString() {
        return "Candidato{" +
                "nome='" + nome + '\'' +
                ", Sobrenome='" + sobrenome + '\'' +
                ", Data de nascimento='" + dataNascimento + '\'' +
                ", E-mail='" + email + '\'' +
                ", CEP='" + cep + '\'' +
                ", País='" + pais + '\'' +
                '}';
    }
}