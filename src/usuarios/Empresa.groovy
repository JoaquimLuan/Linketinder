package usuarios

class Empresa extends Pessoa {
    String pais
    long cnpj
    List competenciasEsperadas

    Empresa(String nome, String email, long cnpj, String pais, String estado, int cep, String descricao, List competenciasEsperadas) {
        super(nome, email, estado, cep, descricao)
        this.cnpj = cnpj
        this.pais = pais
        this.competenciasEsperadas = competenciasEsperadas
    }


    @Override
    String getTipo() {
        return null
    }
}