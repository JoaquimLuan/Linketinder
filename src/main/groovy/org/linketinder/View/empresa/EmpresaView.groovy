package org.linketinder.View.empresa

import org.linketinder.Controller.empresa.EmpresaController
import org.linketinder.Model.EmpresaModel
import org.linketinder.Utilis.Regex
import org.linketinder.Utilis.RegexPattern
import org.linketinder.View.pais.PaisView

class EmpresaView {

    EmpresaController empresaController = new EmpresaController()
    CompetenciaEmpresaView competenciaEmpresaView = new CompetenciaEmpresaView()
    Regex regex = new Regex()
    PaisView paisView = new PaisView()
    EmpresaModel newEmpresa = new EmpresaModel()

    Scanner scanner = new Scanner(System.in)

    void mostrarEmpresas(){
        List empresas = empresaController.listaEmpresas()
        printaEmpresa(empresas)
    }

    private static void printaEmpresa(ArrayList empresas) {
        int count = 1
        println("Listando empresas ------");
        println("------------------------");
        empresas.forEach { empresa ->
            println("Nome: ${empresa.nome}")
            println("Cnpj: ${empresa.cnpj}")
            println("Email: ${empresa.email}")
            println("Cep: ${empresa.cep}")
            println("País: ${empresa.pais}")
            println("------------------------");
            count++
        }
    }

    int inserirEmpresaView(EmpresaModel newEmpresa, int idPais) {
        empresaController.inserirEmpresaController(newEmpresa, idPais)
    }

    EmpresaModel adicionarEmpresa() {

        println("Digite o nome da empresa: ")
        newEmpresa.setNome(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.NOME, newEmpresa.getNome())) {
            println("Nome invalido, digite um nome valido: ")
            newEmpresa.setNome(scanner.nextLine())
        }

        println("Digite o seu Cnpj: ")
        newEmpresa.setCnpj(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.CNPJ, newEmpresa.getCnpj())) {
            println("Cnpj invalido, digite um Cnpj valido: ")
            newEmpresa.setCnpj(scanner.nextLine())
        }

        println("Digite o seu Email: ")
        newEmpresa.setEmail(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.EMAIL, newEmpresa.getEmail())) {
            println("Email invalido, digite um Email valido: ")
            newEmpresa.setEmail(scanner.nextLine())
        }

        println("Digite o seu Cep: ")
        newEmpresa.setCep(scanner.nextLine())
        while (!regex.validarEntrada(RegexPattern.CEP, newEmpresa.getCep())) {
            println("Cep invalido, digite um Cep valido: ")
            newEmpresa.setCep(scanner.nextLine())
        }

        println "Digite o seu País: "
        String pais = scanner.nextLine()
        while (!regex.validarEntrada(RegexPattern.PAIS, pais)) {
            println "País inválido, digite um País válido: "
            pais = scanner.nextLine()
        }
        newEmpresa.setPais(pais)

        int idPais = paisView.vinculaPaisView(newEmpresa.getPais(), "pais").toInteger()
        int idEmpresa = inserirEmpresaView(newEmpresa, idPais)

        boolean continuar = true

        while (continuar) {
            println("Deseja adicionar competência? (S/N): ")
            String resposta = scanner.nextLine()

            if (resposta.equalsIgnoreCase("S")) {
                println("Informe o nome da competência: ")
                String nomeCompetencia = scanner.nextLine()

                if (regex.validarEntrada(RegexPattern.NOME, nomeCompetencia)) {

                    int idCompetencias = competenciaEmpresaView.verificarCompetenciaView(nomeCompetencia)
                    if (idCompetencias == -1) {
                        idCompetencias = competenciaEmpresaView.inserirCompetenciaView(nomeCompetencia)
                    }

                    competenciaEmpresaView.inserirHabilidadeEmpresaView(idEmpresa, idCompetencias)
                    println("Competência adicionada com sucesso!")
                } else {
                    println("Nome de competência inválido. Tente novamente.")
                }
            } else if (resposta.equalsIgnoreCase("N")) {
                continuar = false;
                println("Empresa foi salva com sucesso");
            } else {
                println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.")
            }
        }
        return newEmpresa
    }

    void deletaEmpresa(Scanner scanner) {
        println("Informe o nome da empresa: ")
        String nomeEmpresa = scanner.nextLine()
        if (empresaController.deletarEmpresaController(nomeEmpresa)) {
            println "A empresa foi deletada com sucesso"
        } else {
            println "Verifique o nome digitado, o dado não foi localizado no banco de dados"
        }
    }
}
