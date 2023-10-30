package org.linketinder.dao.empresa

import org.linketinder.Model.EmpresaModel

interface IEmpresaDao {

    List listarEmpresas()

    int inserirEmpresa(EmpresaModel empresa, int idPais)

    boolean deletarEmpresa(String nomeEmpresa)

}