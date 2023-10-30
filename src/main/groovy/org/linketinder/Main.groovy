package org.linketinder

import org.linketinder.View.candidato.MenuCandidatoView
import org.linketinder.View.empresa.MenuEmpresaView
import org.linketinder.View.vaga.MenuVagasView

static void main(String[] args) {

    MenuCandidatoView menuCandidatoView = new MenuCandidatoView()

    Scanner scanner = new Scanner(System.in)
    loopMenu: while (true) {
        println("\nBem Vindo ao Linketinder Escolha uma opção:\n")
        println("1 - Menu Candidatos")
        println("2 - Menu Empresas")
        println("3 - Menu vagas")
        println("0 - Sair")
        String opcao = scanner.nextLine()

        switch (opcao) {
            case "1":
                menuCandidatoView.menuCandidato()
            break
            case "2":
                MenuEmpresaView.menuEmpresa()
            break
            case "3":
                MenuVagasView.menuVaga()
            break
            case "0":
                println("Encerrando o programa...")
                break loopMenu
            default:
                println("Opção inválida!")
        }
    }
}
