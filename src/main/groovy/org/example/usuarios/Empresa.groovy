package org.example.usuarios

class Empresa extends Pessoa {
    long cnpj


    Empresa(String nome, long cnpj, String email, int cep, String pais) {
        super(nome, email, cep, pais)
        this.cnpj = cnpj
    }

    static def adicionarEmpresas(empresas, scanner) {
        String regexNomeEmpresa = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
        String regexCnpj = /^\d{14}$/
        String regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        String regexCep = /^\d{8}$/
        String regexPais = /^[A-Za-z\s]{1,35}$/

        String nome = validarEntrada(regexNomeEmpresa, "Nome: ", scanner)
        long cnpj = validarEntrada(regexCnpj, "CNPJ: ", scanner) as long
        String email = validarEntrada(regexEmail, "Email: ", scanner)
        int cep = validarEntrada(regexCep, "CEP: ", scanner) as int
        String pais = validarEntrada(regexPais, "País: ", scanner)


        Empresa empresa = new Empresa(nome, cnpj, email, cep, pais);
        empresas.add(empresa)
    }

    static def validarEntrada(regex, String mensagem, Scanner scanner) {
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


//    static void salvarEmpresasEmArquivo(empresas) {
//        def empresasArquivo = new File("empresas.txt")
//        empresasArquivo.withWriterAppend { writer ->
//            empresas.each { empresa ->
//                writer << "Empresa\n"
//                writer << "Nome:${empresa.nome}\n"
//                writer << "Email Corporativo:${empresa.email}\n"
//                writer << "CNPJ:${empresa.cnpj}\n"
//                writer << "País:${empresa.pais}\n"
//                writer << "Estado:${empresa.estado}\n"
//                writer << "CEP:${empresa.cep}\n"
//                writer << "Descrição:${empresa.descricao}\n"
//                writer << "Competências Esperadas:${empresa.competenciasEsperadas.join(', ')}\n\n"
//            }
//        }
//    }
//
//    static def listarEmpresas(empresas) {
//        println("\nLista de Empresas:")
//        empresas.each { empresa ->
//            println("Nome: ${empresa.nome}")
//            println("Email Corporativo: ${empresa.email}")
//            println("País: ${empresa.pais}")
//            println("Estado: ${empresa.estado}")
//            println("CEP: ${empresa.cep}")
//            println("Descrição: ${empresa.descricao}")
//            println("Competências Esperadas: ${empresa.competenciasEsperadas.join(', ')}")
//            println()
//        }
//
//        println("\nEmpresas armazenadas no arquivo:")
//        def empresasArquivo = new File("empresas.txt")
//        println(empresasArquivo.text)
//    }



}