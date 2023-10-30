package org.linketinder.Controller.empresa

import org.linketinder.dao.empresa.EmpresaDao

class EmpresaController {

    EmpresaDao empresaBd = new  EmpresaDao()

    List listaEmpresas(){
        return empresaBd.listarEmpresas()
    }

    int inserirEmpresaController(newEmpresa, idPais) {
        return empresaBd.inserirEmpresa(newEmpresa, idPais)
    }

    boolean deletarEmpresaController(String nomeEmpresa) {
        return empresaBd.deletarEmpresa(nomeEmpresa)
    }
}
