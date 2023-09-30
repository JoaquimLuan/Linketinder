package org.example

import org.example.dao.CandidatoBd
import org.example.dao.EmpresaBd
import org.example.dao.VagasBd

static void main(String[] args) {

    def scanner = new Scanner(System.in)

    loopMenu: while (true) {
        println("\nEscolha uma opção:")
        println("1 - Listar Candidatos")
        println("2 - Listar Empresas")
        println("3 - Adicionar Candidato")
        println("4 - Adicionar Empresas")
        println("5 - Listar vagas")
        println("6 - Adicionar vaga")
        println("7 - Deletar Candidato")
        println("8 - Deletar Empresa")
        println("9 - Deletar vaga")
        println("0 - Sair")
        def opcao = scanner.nextLine()

        switch (opcao) {
            case "1":
                CandidatoBd.listarCandidato()
                break
            case "2":
                EmpresaBd.listarEmpresas()
                break
            case "3":
                CandidatoBd.inserirCandidato()
                break
            case "4":
                EmpresaBd.inserirEmpresa()
                break
            case "5":
                VagasBd.listarVagas()
                break
            case "6":
                VagasBd.inserirVaga()
                break
            case "7":
                CandidatoBd.deletarCandidato()
                break
            case "8":
                EmpresaBd.deletarEmpresa()
                break
            case "9":
                VagasBd.deletarVaga()
                break
            case "0":
                println("Encerrando o programa...")
                break loopMenu
            default:
                println("Opção inválida!")
        }
    }
}
