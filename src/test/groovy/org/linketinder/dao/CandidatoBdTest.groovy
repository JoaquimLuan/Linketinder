package org.linketinder.dao

import org.junit.jupiter.api.Test
import org.linketinder.Model.CandidatoModel;

import static org.junit.jupiter.api.Assertions.*;

class CandidatoBdTest {


    private CandidatoDao dao = new CandidatoDao();

    @Test
    void testListarCandidatos() {
        List<CandidatoModel> candidatos = dao.listar()
        assertNotNull(candidatos)
        assertTrue(candidatos.size() > 0)

    }

    @Test
    void testInserirCandidato() {
        CandidatoModel candidato = new CandidatoModel()
        candidato.setNome("João")
        candidato.setSobrenome("Silva")
        candidato.setDataNascimento(new Date())
        candidato.setEmail("joao@example.com")
        candidato.setCpf("123456789")
        candidato.setCep("12345")

        int idCandidato = dao.inserir(candidato, 1)

        assertTrue(idCandidato > 0)

    }

    @Test
     void testDeletarCandidatoExistente() {
        String nomeCandidato = "João"
        boolean deleted = dao.deletar(nomeCandidato)
        assertTrue(deleted)
    }

    @Test
    void testDeletarCandidatoInexistente() {
        String nomeCandidato = "CandidatoInexistente"
        boolean deleted = dao.deletar(nomeCandidato)
        assertFalse(deleted)
    }


}
