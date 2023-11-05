package org.linketinder.Utilis

import java.util.regex.Pattern;
import java.util.regex.Matcher;

class Regex {

    boolean validarEntrada(RegexPattern regexPattern, String entrada) {
        return entrada.matches(regexPattern.getPattern())
    }

    boolean validarEntradaDouble(RegexPattern regexPattern, double entrada) {
        String entradaStr = Double.toString(entrada)
        Pattern pattern = Pattern.compile(regexPattern.getPattern())
        Matcher matcher = pattern.matcher(entradaStr)
        return matcher.matches()
    }
}
