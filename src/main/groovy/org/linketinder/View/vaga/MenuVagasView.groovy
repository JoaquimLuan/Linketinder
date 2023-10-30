package org.linketinder.View.vaga

class MenuVagasView {

    static void menuVaga(){

        Scanner scanner = new Scanner(System.in)
        VagaView vagaView = new VagaView();

        while (true) {
            println("\nEscolha uma opção:")
            println("1 - Listar Vagas")
            println("2 - Adicionar Vaga")
            println("3 - Deletar Vaga")
            println("4 - Retornar")
            String opcao = scanner.nextLine()

            switch (opcao) {
                case "1":
                    vagaView.mostrarVagas()
                    break
                case "2":
                    vagaView.adicionarVaga()
                    break
                case "3":
                    vagaView.deletaVaga(scanner)
                    break
                case "4":
                    return
                default:
                    println("Opção inválida!")
            }
        }
    }
}
