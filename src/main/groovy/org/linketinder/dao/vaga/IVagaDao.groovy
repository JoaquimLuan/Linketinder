package org.linketinder.dao.vaga

import org.linketinder.Model.VagaModel

interface IVagaDao {

    List listarVagas()

    int inserirVaga(VagaModel vaga)

    boolean deletarVaga(String nomeVaga)
}
