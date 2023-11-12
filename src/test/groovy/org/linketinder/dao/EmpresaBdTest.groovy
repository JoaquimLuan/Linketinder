package org.linketinder.dao

import org.linketinder.Model.EmpresaModel
import org.junit.jupiter.api.Test

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertTrue;

class EmpresaBdTest {

    private EmpresaDao dao = new EmpresaDao()

    @Test
    void testListarEmpresas() {
        List<EmpresaModel> empresas = dao.listar()
        assertNotNull(empresas)
        assertTrue(empresas.size() > 0)


    }

    @Test
    void testInserirEmpresa() {
        EmpresaModel empresa = new EmpresaModel()
        empresa.setNome("Minha Empresa")
        empresa.setCnpj("123456789")
        empresa.setEmail("empresa@example.com")
        empresa.setCep("12345")

        int idEmpresa = dao.inserir(empresa, 1)

        assertTrue(idEmpresa > 0);
    }

    @Test
    void testDeletarEmpresaExistente() {
        String nomeEmpresa = "Minha Empresa"
        boolean deleted = dao.deletar(nomeEmpresa)
        assertTrue(deleted)
    }

    @Test
    void testDeletarEmpresaInexistente() {
        String nomeEmpresa = "EmpresaInexistente"
        boolean deleted = dao.deletar(nomeEmpresa)
        assertFalse(deleted)
    }


}