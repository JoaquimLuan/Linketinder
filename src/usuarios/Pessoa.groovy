package usuarios

abstract class Pessoa {
    String nome
    String email
    String estado
    int cep
    String descricao


    Pessoa(String nome, String email, String estado, int cep, String descricao) {
        this.nome = nome
        this.email = email
        this.estado = estado
        this.cep = cep
        this.descricao = descricao
    }

    abstract String getTipo()

}
