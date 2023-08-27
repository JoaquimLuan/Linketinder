package org.example.usuarios

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test;


public class CandidatoTest {

    private List<Candidato> candidatos;

    @BeforeEach
    public void setUp() {
        candidatos = new ArrayList<>();
    }

    @Test
    public void testAdicionarCandidato() {
        List<String> competencias = new ArrayList<>();
        competencias.add("Java");
        competencias.add("Python");
        competencias.add("SQL");

        Candidato.adicionarCandidato(candidatos, "João", "joao@example.com", 12345678901L, 25, "SP", 12345678, "Descrição", competencias);

        Assertions.assertEquals(1, candidatos.size());
        Assertions.assertEquals("João", candidatos.get(0).getNome());
        Assertions.assertEquals("joao@example.com", candidatos.get(0).getEmail());
        Assertions.assertEquals(12345678901L, candidatos.get(0).getCpf());
        Assertions.assertEquals(25, candidatos.get(0).getIdade());
        Assertions.assertEquals("SP", candidatos.get(0).getEstado());
        Assertions.assertEquals(12345678, candidatos.get(0).getCep());
        Assertions.assertEquals("Descrição", candidatos.get(0).getDescricao());
        Assertions.assertEquals(competencias, candidatos.get(0).getCompetencias());
    }
}