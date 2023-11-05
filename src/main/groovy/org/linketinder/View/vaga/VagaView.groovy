package org.linketinder.View.vaga

import org.linketinder.Controller.vaga.VagasController
import org.linketinder.Utilis.Regex
import org.linketinder.Utilis.RegexPattern
import org.linketinder.Model.VagaModel

class VagaView {

    CompetenciaVagaView competenciaVagaView = new CompetenciaVagaView()
    VagasController vagasController = new VagasController()
    VagaModel newVaga = new VagaModel()
    Regex regex = new Regex()

    Scanner scanner = new Scanner(System.in)

    void mostrarVagas(){
        List vagas = vagasController.listaVagas()
        printaVagas(vagas)
    }

    private static void printaVagas(ArrayList vagas) {
        int count = 1
        println("Listando vagas ------");
        println("------------------------");
        vagas.forEach { vaga ->
            println("Nome: ${vaga.nome}")
            println("Descrição: ${vaga.descricao}")
            println("Salário: ${vaga.salario}")
            println("Empresa: ${vaga.empresa}")
            println("------------------------");
            count++
        }
    }

    int inserirVagaView(VagaModel newVaga) {
        vagasController.inserirVagaController(newVaga)
    }

    VagaModel adicionarVaga() {

        println("Digite o nome da vaga: ")
        newVaga.setNome(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.NOME, newVaga.getNome())) {
            println("Nome invalido, digite um nome valido: ")
            newVaga.setNome(scanner.nextLine())
        }

        println("Digite a descrição da vaga: ")
        newVaga.setDescricao(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.DESCRICAO, newVaga.getDescricao())) {
            println("Descrição invalida, digite uma descrição valida: ")
            newVaga.setDescricao(scanner.nextLine())
        }

        println("Digite o salário: ")
        newVaga.setSalario(scanner.nextDouble())
        while (!regex.validarEntradaDouble(RegexPattern.SALARIO, newVaga.getSalario())) {
            println("Salario invalido, digite um Salario valido: ")
            newVaga.setSalario(scanner.nextDouble())
        }
        scanner.nextLine()

        println("Digite o nome da empresa: ")
        newVaga.setEmpresa(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.NOME, newVaga.getEmpresa())) {
            println("Empresa inválido, digite uma empresa já cadastrada: ")
            newVaga.setEmpresa(scanner.nextLine())
        }

        int idVaga = inserirVagaView(newVaga)

        boolean continuar = true

        while (continuar) {
            println("Deseja adicionar competência referente a vaga? (S/N): ");
            String resposta = scanner.nextLine()

            if (resposta.equalsIgnoreCase("S")) {
                println("Informe o nome da competência: ")
                String nomeCompetencia = scanner.nextLine()

                if (regex.validarEntrada(RegexPattern.NOME, nomeCompetencia)) {

                    int idCompetencias = competenciaVagaView.verificarCompetenciaView(nomeCompetencia)
                    if (idCompetencias == -1) {
                        idCompetencias = competenciaVagaView.inserirCompetenciaView(nomeCompetencia)
                    }

                    competenciaVagaView.inserirHabilidadeVagaView(idVaga, idCompetencias)
                    println("Competência adicionada com sucesso!")
                } else {
                    println("Nome de competência inválido. Tente novamente.")
                }
            } else if (resposta.equalsIgnoreCase("N")) {
                continuar = false;
                println("Vaga foi salva com sucesso")
            } else {
                println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.")
            }
        }
        return newVaga
    }

    void deletaVaga(Scanner scanner) {
        println("Informe o nome da Vaga: ")
        String nomeVaga = scanner.nextLine()
        if (vagasController.deletarVagaController(nomeVaga)) {
            println "A vaga foi deletada com sucesso"
        } else {
            println "Verifique o nome digitado, o dado não foi localizado no banco de dados"
        }
    }
}
