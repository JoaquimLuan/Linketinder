package org.linketinder.View.vaga

import org.linketinder.Controller.vaga.VagaCompetenciaController

class CompetenciaVagaView {

    static verificarCompetenciaView(String nomeCompetencia) {
        VagaCompetenciaController.verificarCompetenciaController(nomeCompetencia)
    }

    static inserirCompetenciaView(String nomeCompetencia) {
        VagaCompetenciaController.inserirCompetenciaController(nomeCompetencia)
    }

    static inserirHabilidadeVagaView(idVaga, idCompetencias) {
        VagaCompetenciaController.inserirHabilidadeVagaController(idVaga, idCompetencias)
    }
}
