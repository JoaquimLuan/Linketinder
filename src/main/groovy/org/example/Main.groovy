package org.example

import org.example.usuarios.Candidato
import org.example.usuarios.Empresa

static void main(String[] args) {
    def candidatos = []
    def empresas = []

    def scanner = new Scanner(System.in)

    loopMenu: while (true) {
        println("\nEscolha uma opção:")
        println("1 - Listar Candidatos")
        println("2 - Listar Empresas")
        println("3 - Adicionar Candidato")
        println("4 - Adicionar Empresas")
        println("0 - Sair")
        def opcao = scanner.nextLine()

        switch (opcao) {
            case "1":
                Candidato.listarCandidatos(candidatos)
                break
            case "2":
                Empresa.listarEmpresas(empresas)
                break
            case "3":
                Candidato.adicionarCandidatos(candidatos, scanner)
                break
            case "4":
                Empresa.adicionarEmpresas(empresas, scanner)
                break
            case "0":
                println("Encerrando o programa...")
                break loopMenu
            default:
                println("Opção inválida!")
        }
    }
}
