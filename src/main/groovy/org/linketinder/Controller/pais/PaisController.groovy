package org.linketinder.Controller.pais

import org.linketinder.dao.pais.PaisDao

class PaisController {

    PaisDao paisBd = new PaisDao()

    String vinculaPaisController(String pais, String string){
        return paisBd.vinculaPais(pais, "pais")
    }
}
