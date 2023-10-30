package org.linketinder.Controller.vaga

import org.linketinder.dao.competencia.CompetenciasDao

class VagaCompetenciaController {

    static verificarCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().verificarCompetencia(nomeCompetencia)
    }

    static inserirCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().inserirCompetencia(nomeCompetencia)
    }

    static inserirHabilidadeVagaController(idVaga, idCompetencias) {
        CompetenciasDao.getInstance().inserirHabilidadeVaga(idVaga, idCompetencias)
    }
}
