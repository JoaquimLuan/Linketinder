package org.linketinder.View.empresa

class MenuEmpresaView {

    static void menuEmpresa(){

        Scanner scanner = new Scanner(System.in)
        EmpresaView empresaView = new EmpresaView();

        while (true) {
            println("\nEscolha uma opção:")
            println("1 - Listar Empresas")
            println("2 - Adicionar Empresa")
            println("3 - Deletar Empresa")
            println("4 - Retornar")
            String opcao = scanner.nextLine()

            switch (opcao) {
                case "1":
                    empresaView.mostrarEmpresas()
                    break
                case "2":
                    empresaView.adicionarEmpresa()
                    break
                case "3":
                    empresaView.deletaEmpresa(scanner)
                    break
                case "4":
                    return
                default:
                    println("Opção inválida!")
            }
        }
    }
}
