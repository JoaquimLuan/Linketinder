package org.example.usuarios

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

import java.text.SimpleDateFormat;


class CandidatoTest {

    private List<Candidato> candidatos;

    @BeforeEach
    void setUp() {
        candidatos = new ArrayList<>();
    }

    @Test
    void testAdicionarCandidato() {

        Scanner scanner = new Scanner("João\nMartins\n14-08-1990\njoao@example.com\n12345678901\n12345678\nBrazil\n");

        Candidato.adicionarCandidato(candidatos, scanner);

        Assertions.assertEquals(1, candidatos.size());
        Assertions.assertEquals("João", candidatos.get(0).getNome());
        Assertions.assertEquals("Martins", candidatos.get(0).getSobrenome());
        Assertions.assertEquals("14-08-1990", new SimpleDateFormat("dd-MM-yyyy").format(candidatos.get(0).getDataNascimento()));
        Assertions.assertEquals("joao@example.com", candidatos.get(0).getEmail());
        Assertions.assertEquals(12345678901L, candidatos.get(0).getCpf());
        Assertions.assertEquals(12345678, candidatos.get(0).getCep());
        Assertions.assertEquals("Brazil", candidatos.get(0).getPais());
    }
}