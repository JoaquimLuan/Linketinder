package org.example.usuarios

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

    static def listarEmpresas(empresas) {
        println("\nLista de Empresas:")
        empresas.each { empresa ->
            println("Nome: ${empresa.nome}")
            println("Email Corporativo: ${empresa.email}")
            println("País: ${empresa.pais}")
            println("Estado: ${empresa.estado}")
            println("CEP: ${empresa.cep}")
            println("Descrição: ${empresa.descricao}")
            println("Competências Esperadas: ${empresa.competenciasEsperadas.join(', ')}")
            println()
        }

        println("\nEmpresas armazenadas no arquivo:")
        def empresasArquivo = new File("empresas.txt")
        println(empresasArquivo.text)
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


    static def adicionarEmpresas(empresas, scanner) {
        def regexNomeEmpresa = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
        def regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        def regexCnpj = /^\d{14}$/
        def regexPais = /^[A-Za-z\s]{1,35}$/
        def regexEstado = /^[a-zA-Z]{2}$/
        def regexCep = /^\d{8}$/
        def regexDescricao = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/

        def nome = validarEntrada(regexNomeEmpresa, "Nome: ", scanner) as String
        def email = validarEntrada(regexEmail, "Email: ", scanner) as String
        def cnpj = validarEntrada(regexCnpj, "CNPJ: ", scanner) as long
        def pais = validarEntrada(regexPais, "País: ", scanner) as String
        def estado = validarEntrada(regexEstado, "Estado: ", scanner) as String
        def cep = validarEntrada(regexCep, "CEP: ", scanner) as int
        def descricao = validarEntrada(regexDescricao, "Descrição: ", scanner) as String

        print("Competências Esperadas (separadas por vírgula): ")
        Scanner entrada = new Scanner(System.in)
        def competenciasEntrada = entrada.nextLine()

        def competenciasEsperadas = competenciasEntrada.split(',').collect { it.trim() }

        adicionarEmpresa(empresas, nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas)
        salvarEmpresasEmArquivo(empresas)
        println("Empresa adicionada com sucesso!\n")
    }

    static def adicionarEmpresa(empresas, nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas) {
        def empresa = new Empresa(nome, email, cnpj, pais, estado, cep, descricao, competenciasEsperadas as List)
        empresas << empresa
        return empresa
    }

    static void salvarEmpresasEmArquivo(empresas) {
        def empresasArquivo = new File("empresas.txt")
        empresasArquivo.withWriterAppend { writer ->
            empresas.each { empresa ->
                writer << "Empresa\n"
                writer << "Nome:${empresa.nome}\n"
                writer << "Email Corporativo:${empresa.email}\n"
                writer << "CNPJ:${empresa.cnpj}\n"
                writer << "País:${empresa.pais}\n"
                writer << "Estado:${empresa.estado}\n"
                writer << "CEP:${empresa.cep}\n"
                writer << "Descrição:${empresa.descricao}\n"
                writer << "Competências Esperadas:${empresa.competenciasEsperadas.join(', ')}\n\n"
            }
        }
    }



}