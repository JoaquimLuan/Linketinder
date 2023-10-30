package org.linketinder.View.empresa

import org.linketinder.Controller.empresa.EmpresaCompetenciaController

class CompetenciaEmpresaView {

    static verificarCompetenciaView(String nomeCompetencia) {
        EmpresaCompetenciaController.verificarCompetenciaController(nomeCompetencia)
    }

    static inserirCompetenciaView(String nomeCompetencia) {
        EmpresaCompetenciaController.inserirCompetenciaController(nomeCompetencia)
    }

    static inserirHabilidadeEmpresaView(idEmpresa, idCompetencias) {
        EmpresaCompetenciaController.inserirHabilidadeEmpresaController(idEmpresa, idCompetencias)
    }
}
