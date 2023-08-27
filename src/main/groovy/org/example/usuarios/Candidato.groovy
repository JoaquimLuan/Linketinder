package org.example.usuarios

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

    static def listarCandidatos(candidatos) {
        println("\nLista de Candidatos:")
        candidatos.each { candidato ->
            println("Nome: ${candidato.nome}")
            println("Email: ${candidato.email}")
            println("Idade: ${candidato.idade}")
            println("Estado: ${candidato.estado}")
            println("CEP: ${candidato.cep}")
            println("Descrição: ${candidato.descricao}")
            println("Competências: ${candidato.competencias.join(', ')}")
            println()
        }

        println("\nCandidatos armazenados no arquivo:")
        def candidatosArquivo = new File("candidatos.txt")
        println(candidatosArquivo.text)
    }

    static def validarEntrada(regex, String mensagem, scanner) {
        while (true) {
            print(mensagem)
            def entrada = scanner.nextLine()

            if (entrada.matches(regex)) {
                return entrada
            } else {
                println("Entrada inválida. Por favor, insira uma entrada válida.")
            }
        }
    }

    static def adicionarCandidatos(candidatos, scanner) {
        def regexNome = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
        def regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        def regexCpf = /^\d{11}$/
        def regexIdade = /^(1[8-9]|[2-9][0-9])$/
        def regexEstado = /^[a-zA-Z]{2}$/
        def regexCep = /^\d{8}$/
        def regexDescricao = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/

        def nome = validarEntrada(regexNome, "Nome: ", scanner)
        def email = validarEntrada(regexEmail, "Email: ", scanner)
        def cpf = validarEntrada(regexCpf, "CPF: ", scanner) as long
        def idade = validarEntrada(regexIdade, "Idade: ", scanner)as int
        def estado = validarEntrada(regexEstado, "Estado: ", scanner)
        def cep = validarEntrada(regexCep, "CEP: ", scanner) as int
        def descricao = validarEntrada(regexDescricao, "Descrição: ", scanner)

        print("Competências (separadas por vírgula): ")
        Scanner entrada = new Scanner(System.in)
        def competenciasEntrada = entrada.nextLine()

        def competencias =  competenciasEntrada.split(',').collect { it.trim() }

        adicionarCandidato(candidatos, nome, email, cpf, idade, estado, cep, descricao, competencias)
        salvarCandidatosEmArquivo(candidatos)
        println("Candidato adicionado com sucesso!\n")
    }

    static def adicionarCandidato(candidatos, nome, email, cpf, idade, estado, cep, descricao, competencias) {
        def candidato = new Candidato(nome, email, cpf, idade, estado, cep, descricao, competencias as List)
        candidatos << candidato
        return candidato
    }

    static void salvarCandidatosEmArquivo(candidatos) {
        def candidatosArquivo = new File("candidatos.txt")
        candidatosArquivo.withWriterAppend { writer ->
            candidatos.each { candidato ->
                writer << "Candidato\n"
                writer << "Nome:${candidato.nome}\n"
                writer << "Email:${candidato.email}\n"
                writer << "CPF:${candidato.cpf}\n"
                writer << "Idade:${candidato.idade}\n"
                writer << "Estado:${candidato.estado}\n"
                writer << "CEP:${candidato.cep}\n"
                writer << "Descrição:${candidato.descricao}\n"
                writer << "Competências:${candidato.competencias.join(', ')}\n\n"
            }
        }
    }


}