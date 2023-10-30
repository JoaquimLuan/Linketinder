package org.linketinder.View.candidato

import org.linketinder.Controller.candidato.CandidatoCompetenciaController

class CompetenciaCandidatoView {

    static verificarCompetenciaView(String nomeCompetencia) {
        CandidatoCompetenciaController.verificarCompetenciaController(nomeCompetencia)
    }

    static inserirCompetenciaView(String nomeCompetencia) {
        CandidatoCompetenciaController.inserirCompetenciaController(nomeCompetencia)
    }

    static inserirHabilidadeCandidatoView(idCandidato, idCompetencias) {
        CandidatoCompetenciaController.inserirHabilidadeCandidatoController(idCandidato, idCompetencias)
    }

}
