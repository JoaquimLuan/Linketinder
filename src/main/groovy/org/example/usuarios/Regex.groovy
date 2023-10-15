package org.example.usuarios

class Regex {

    static String regexNome = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,35}$/
    static String regexCnpj = /^\d{14}$/
    static String regexEmail = '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$'
    static String regexCep = /^\d{8}$/
    static String regexPais = /^[A-Za-z\s]{1,35}$/
    static String regexNascimento = /^(0[1-9]|[12][0-9]|3[01])-(0[1-9]|1[0-2])-\d{4}$/
    static String regexCpf = /^\d{11}$/
    static String regexDrescricao = /^[A-Za-zÀ-ÖØ-öø-ÿ\s]{1,50}$/
    static String regexSalario = /^\d+(\.\d{1,2})?$/


    static validarEntrada(regex, String mensagem, Scanner scanner) {
        while (true) {
            print(mensagem)
            String entrada = scanner.nextLine()

            if (entrada.matches(regex)) {
                return entrada
            } else {
                println("Entrada inválida. Por favor, insira uma entrada válida.")
            }
        }
    }
}
