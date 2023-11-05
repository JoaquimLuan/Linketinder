package org.linketinder.View.candidato

import org.linketinder.Controller.candidato.CandidatoController
import org.linketinder.Model.CandidatoModel
import org.linketinder.Utilis.Regex
import org.linketinder.Utilis.RegexPattern
import org.linketinder.View.pais.PaisView

import java.text.SimpleDateFormat

class CandidatoView {

    CandidatoController candidatoController = new CandidatoController()
    CompetenciaCandidatoView competenciaCandidatoView = new CompetenciaCandidatoView()
    Regex regex = new Regex()
    PaisView paisView = new PaisView()
    CandidatoModel newCandidate = new CandidatoModel()

    Scanner scanner = new Scanner(System.in)

    void mostrarCandidatos(){
        List candidatos = candidatoController.listaUsuarios()
        printaCandidato(candidatos)
    }

    private static void printaCandidato(ArrayList candidatos) {
        int count = 1
        println("Listando candidatos ------");
        println("------------------------");
        candidatos.forEach { candidato ->
            println("Nome: ${candidato.nome}")
            println("Sobrenome: ${candidato.sobrenome}")
            println("Data de Nascimento: ${candidato.dataNascimento}")
            println("Email: ${candidato.email}")
            println("Cep: ${candidato.cep}")
            println("País: ${candidato.pais}")
            println("------------------------");
            count++
        }
    }

    int inserirCandidatoView(CandidatoModel newCandidate, int idPais) {
        candidatoController.inserirCandidatoController(newCandidate, idPais)
    }

    CandidatoModel adicionarCandidato() {

        println("Digite o nome: ")
        newCandidate.setNome(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.NOME, newCandidate.getNome())) {
            println("Nome invalido, digite um nome valido: ")
            newCandidate.setNome(scanner.nextLine())
        }

        println("Digite o seu sobrenome: ")
        newCandidate.setSobrenome(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.NOME, newCandidate.getSobrenome())) {
            println("Sobrenome invalido, digite um Sobrenome valido: ")
            newCandidate.setSobrenome(scanner.nextLine())
        }

        println "Digite sua data de nascimento no formato dd-MM-yyyy: "
        String dataNascimentoStr = scanner.nextLine()
        while (!regex.validarEntrada(RegexPattern.NASCIMENTO, dataNascimentoStr)) {
            println("Data de nascimento inválida, digite uma data de nascimento válida no formato dd-MM-yyyy: ")
            dataNascimentoStr = scanner.nextLine()
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy")
        Date dataNascimento = dateFormat.parse(dataNascimentoStr)
        newCandidate.setDataNascimento(dataNascimento)

        println("Digite o seu Email: ")
        newCandidate.setEmail(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.EMAIL, newCandidate.getEmail())) {
            println("Email invalido, digite um Email valido: ")
            newCandidate.setEmail(scanner.nextLine())
        }

        println("Digite o seu CPF: ")
        newCandidate.setCpf(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.CPF, newCandidate.getCpf())) {
            println("CPF invalido, digite um CPF valido: ")
            newCandidate.setCpf(scanner.nextLine())
        }

        println("Digite o seu Cep: ")
        newCandidate.setCep(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.CEP, newCandidate.getCep())) {
            println("Cep invalido, digite um Cep valido: ")
            newCandidate.setCep(scanner.nextLine())
        }

        println "Digite o seu País: "
        String pais = scanner.nextLine()
        while (!regex.validarEntrada(RegexPattern.PAIS, pais)) {
            println "País inválido, digite um País válido: "
            pais = scanner.nextLine()
        }
        newCandidate.setPais(pais)

        int idPais = paisView.vinculaPaisView(newCandidate.getPais(), "pais").toInteger()
        int idCandidato = inserirCandidatoView(newCandidate, idPais)

        boolean continuar = true

        while (continuar) {
            println("Deseja adicionar competência? (S/N): ")
            String resposta = scanner.nextLine()

            if (resposta.equalsIgnoreCase("S")) {
                println("Informe o nome da competência: ")
                String nomeCompetencia = scanner.nextLine()

                if (regex.validarEntrada(RegexPattern.NOME, nomeCompetencia)) {

                    int idCompetencias = competenciaCandidatoView.verificarCompetenciaView(nomeCompetencia)
                    if (idCompetencias == -1) {
                        idCompetencias = competenciaCandidatoView.inserirCompetenciaView(nomeCompetencia)
                    }

                    competenciaCandidatoView.inserirHabilidadeCandidatoView(idCandidato, idCompetencias)
                    println("Competência adicionada com sucesso!");
                } else {
                    println("Nome de competência inválido. Tente novamente.")
                }
            } else if (resposta.equalsIgnoreCase("N")) {
                continuar = false;
                println("Candidato foi salvo com sucesso");
            } else {
                println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.");
            }
        }
        return newCandidate
    }

    void deletaCandidato(Scanner scanner) {
        println("Informe o nome do candidato: ")
        String nomeCandidato = scanner.nextLine()
        if (candidatoController.deletarCandidatoController(nomeCandidato)) {
            println "O candidato foi deletado com sucesso"
        } else {
            println "Verifique o nome digitado, o nome não foi localizado no banco de dados"
        }
    }
}
