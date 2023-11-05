package org.linketinder.Controller.candidato

import org.linketinder.dao.CandidatoDao

class CandidatoController {

    CandidatoDao candidatoBd = new CandidatoDao()

    List listaUsuarios(){
        return candidatoBd.listar()
    }

    int inserirCandidatoController(newCandidate, idPais) {
        return candidatoBd.inserir(newCandidate, idPais)
    }

    boolean deletarCandidatoController(String nomeCandidato) {
        return candidatoBd.deletar(nomeCandidato);
    }

}
