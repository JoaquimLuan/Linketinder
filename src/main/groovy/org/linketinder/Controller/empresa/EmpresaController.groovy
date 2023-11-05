package org.linketinder.Controller.empresa

import org.linketinder.dao.EmpresaDao

class EmpresaController {

    EmpresaDao empresaBd = new  EmpresaDao()

    List listaEmpresas(){
        return empresaBd.listar()
    }

    int inserirEmpresaController(newEmpresa, idPais) {
        return empresaBd.inserir(newEmpresa, idPais)
    }

    boolean deletarEmpresaController(String nomeEmpresa) {
        return empresaBd.deletar(nomeEmpresa)
    }
}
