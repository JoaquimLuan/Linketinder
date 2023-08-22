package usuarios

class Candidato extends Pessoa {
    int idade
    long cpf
    List <String> competencias


    Candidato(String nome, String email, long cpf, int idade, String estado, int cep, String descricao, List<String> competencias) {
        super(nome, email, estado, cep, descricao)
        this.idade = idade
        this.cpf = cpf
        this.competencias = competencias
    }


    @Override
    String getTipo() {
        return null
    }
}