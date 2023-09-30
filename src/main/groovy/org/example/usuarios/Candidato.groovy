package org.example.usuarios

import java.text.ParseException
import java.text.SimpleDateFormat

class Candidato extends Pessoa {
    String sobrenome
    Date dataNascimento
    long cpf



    Candidato(String nome, String sobrenome, Date dataNascimento, String email, long cpf, int cep, String pais) {
        super(nome, email, cep,  pais)
        this.sobrenome = sobrenome
        this.dataNascimento = dataNascimento
        this.cpf = cpf
    }


    static void adicionarCandidato(List<Candidato> candidatos, Scanner scanner) {
        String regexNome = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,30}$/
        String regexSobrenome = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,30}$/
        String regexNascimento = /^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$/
        String regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
        String regexCpf = /^\d{11}$/
        String regexCep = /^\d{8}$/
        String regexPais = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,30}$/

        String nome = validarEntrada(regexNome, "Nome: ", scanner);
        String sobrenome = validarEntrada(regexSobrenome, "Sobrenome: ", scanner);
        Date dataNascimento = validarEntradaData(regexNascimento, "Data de nascimento (formato dd-MM-yyyy): ", scanner);
        String email = validarEntrada(regexEmail, "Email: ", scanner);
        long cpf = Long.parseLong(validarEntrada(regexCpf, "CPF: ", scanner))
        int cep = validarEntrada(regexCep, "CEP: ", scanner) as int
        String pais = validarEntrada(regexPais, "País: ", scanner);

        Candidato candidato = new Candidato(nome, sobrenome, dataNascimento, email, cpf, cep, pais);
        candidatos.add(candidato);
    }

    static String validarEntrada(String regex, String mensagem, Scanner scanner) {
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            if (entrada.matches(regex)) {
                return entrada;
            } else {
                System.out.println("Entrada inválida. Por favor, insira uma entrada válida.");
            }
        }
    }

    static Date validarEntradaData(String regex, String mensagem, Scanner scanner) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            if (entrada.matches(regex)) {
                try {
                    return sdf.parse(entrada);
                } catch (ParseException e) {
                    System.err.println("Formato de data inválido. Use o formato dd-MM-yyyy.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira uma data válida.");
            }
        }
    }

    // Foi implementado persistencia no BD
//    static void salvarCandidatosEmArquivo(candidatos) {
//        def candidatosArquivo = new File("candidatos.txt")
//        candidatosArquivo.withWriterAppend { writer ->
//            candidatos.each { candidato ->
//                writer << "Candidato\n"
//                writer << "Nome:${candidato.nome}\n"
//                writer << "Email:${candidato.email}\n"
//                writer << "CPF:${candidato.cpf}\n"
//                writer << "Idade:${candidato.idade}\n"
//                writer << "Estado:${candidato.estado}\n"
//                writer << "CEP:${candidato.cep}\n"
//                writer << "Descrição:${candidato.descricao}\n"
//                writer << "Competências:${candidato.competencias.join(', ')}\n\n"
//            }
//        }
//    }

    // Foi implementado persistencia no BD

//    static def listarCandidatos(candidatos) {
//        println("\nLista de Candidatos:")
//        candidatos.each { candidato ->
//            println("Nome: ${candidato.nome}")
//            println("Email: ${candidato.email}")
//            println("Idade: ${candidato.idade}")
//            println("Estado: ${candidato.estado}")
//            println("CEP: ${candidato.cep}")
//            println("Descrição: ${candidato.descricao}")
//            println("Competências: ${candidato.competencias.join(', ')}")
//            println()
//        }
//
//        println("\nCandidatos armazenados no arquivo:")
//        def candidatosArquivo = new File("candidatos.txt")
//        println(candidatosArquivo.text)
//    }

}