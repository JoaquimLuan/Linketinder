package org.linketinder.View.candidato

class MenuCandidatoView {

    CandidatoView candidatoView = new CandidatoView()

    void menuCandidato(){

        Scanner scanner = new Scanner(System.in)

        while (true) {
            println("\nEscolha uma opção:")
            println("1 - Listar Candidatos")
            println("2 - Adicionar Candidato")
            println("3 - Deletar Candidato")
            println("4 - Retornar")
            String opcao = scanner.nextLine()

            switch (opcao) {
                case "1":
                    candidatoView.mostrarCandidatos()
                    break
                case "2":
                    candidatoView.adicionarCandidato()
                    break
                case "3":
                    candidatoView.deletaCandidato(scanner)
                    break
                case "4":
                    return
                default:
                    println("Opção inválida!")
            }
        }
    }
}
