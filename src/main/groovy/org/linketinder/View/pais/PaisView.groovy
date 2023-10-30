package org.linketinder.View.pais

import org.linketinder.Controller.pais.PaisController

class PaisView {

    PaisController paisController = new PaisController()

    String vinculaPaisView(String pais, String string){
        paisController.vinculaPaisController(pais, "pais")
    }
}
