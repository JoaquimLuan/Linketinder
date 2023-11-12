package org.linketinder.dao

import org.junit.jupiter.api.Test
import org.linketinder.Model.VagaModel;

import static org.junit.jupiter.api.Assertions.*;

class VagaBdTest {

    private VagasDao dao = new VagasDao();

    @Test
    void testListarVagas() {
        List<VagaModel> vagas = dao.listarVagas()
        assertNotNull(vagas)
        assertTrue(vagas.size() > 0)

    }


    @Test
    void testDeletarVagaExistente() {
        String nomeVaga = "Minha Vaga"
        boolean deleted = dao.deletarVaga(nomeVaga)
        assertTrue(deleted)
    }

}
