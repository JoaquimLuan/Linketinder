package org.linketinder.Controller.candidato

import org.linketinder.dao.CompetenciasDao

class CandidatoCompetenciaController {

    static verificarCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().verificarCompetencia(nomeCompetencia)
    }

    static inserirCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().inserirCompetencia(nomeCompetencia)
    }

    static inserirHabilidadeCandidatoController(idCandidato, idCompetencias) {
        CompetenciasDao.getInstance().inserirHabilidadeCandidato(idCandidato, idCompetencias)
    }
}
