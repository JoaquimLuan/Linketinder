package org.linketinder.Controller.empresa

import org.linketinder.dao.CompetenciasDao

class EmpresaCompetenciaController {

    static verificarCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().verificarCompetencia(nomeCompetencia)
    }

    static inserirCompetenciaController(String nomeCompetencia) {
        CompetenciasDao.getInstance().inserirCompetencia(nomeCompetencia)
    }

    static inserirHabilidadeEmpresaController(idEmpresa, idCompetencias) {
        CompetenciasDao.getInstance().inserirHabilidadeEmpresa(idEmpresa, idCompetencias)
    }
}
