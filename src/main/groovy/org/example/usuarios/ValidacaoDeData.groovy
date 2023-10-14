package org.example.usuarios

import java.text.ParseException
import java.text.SimpleDateFormat

class ValidacaoDeData {

    static Date validarEntradaData(String regex, String mensagem, Scanner scanner) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        while (true) {
            System.out.print(mensagem);
            String entrada = scanner.nextLine();

            if (entrada.matches(regex)) {
                try {
                    return sdf.parse(entrada);
                } catch (ParseException e) {
                    System.err.println("Formato de data inválido. Use o formato dd-MM-yyyy.");
                }
            } else {
                System.out.println("Entrada inválida. Por favor, insira uma data válida.");
            }
        }
    }
}
