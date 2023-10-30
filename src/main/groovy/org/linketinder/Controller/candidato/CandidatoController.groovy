package org.linketinder.Controller.candidato

import org.linketinder.dao.candidato.CandidatoDao

class CandidatoController {

    CandidatoDao candidatoBd = new CandidatoDao()

    List listaUsuarios(){
        return candidatoBd.listarCandidato()
    }

    int inserirCandidatoController(newCandidate, idPais) {
        return candidatoBd.inserirCandidato(newCandidate, idPais)
    }

    boolean deletarCandidatoController(String nomeCandidato) {
        return candidatoBd.deletarCandidato(nomeCandidato);
    }

}
