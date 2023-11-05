package org.linketinder.Controller.vaga

import org.linketinder.dao.VagasDao

class VagasController {

    VagasDao vagasBd = new VagasDao()

    List listaVagas(){
        return vagasBd.listarVagas()
    }

    int inserirVagaController(newVaga) {
        return vagasBd.inserirVaga(newVaga)
    }

    boolean deletarVagaController(String nomeVaga) {
        return vagasBd.deletarVaga(nomeVaga);
    }
}
