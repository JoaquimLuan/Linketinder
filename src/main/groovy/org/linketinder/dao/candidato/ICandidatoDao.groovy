package org.linketinder.dao.candidato

import org.linketinder.Model.CandidatoModel

interface ICandidatoDao {

    List listarCandidato()

    int inserirCandidato(CandidatoModel candidato, int idPais)

    boolean deletarCandidato(String nomeCandidato)

}