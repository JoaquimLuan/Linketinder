package org.linketinder.Model

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class EmpresaModelTest {

    private List<EmpresaModel> empresas;

    @BeforeEach
    void setUp() {
        empresas = new ArrayList<>();
    }

    @Test
    void testAdicionarEmpresa() {

        Scanner scanner = new Scanner("EmpresaXYZ\n12345678901234\ncontato@empresa.com\n12345678\nBrasil");


        EmpresaModel.adicionarEmpresas(empresas, scanner );

        Assertions.assertEquals(1, empresas.size());
        Assertions.assertEquals("EmpresaXYZ", empresas.get(0).getNome());
        Assertions.assertEquals(12345678901234L, empresas.get(0).getCnpj());
        Assertions.assertEquals("contato@empresa.com", empresas.get(0).getEmail());
        Assertions.assertEquals(12345678, empresas.get(0).getCep());
        Assertions.assertEquals("Brasil", empresas.get(0).getPais());

    }
}