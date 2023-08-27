package org.example.usuarios

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmpresaTest {

    private List<Empresa> empresas;

    @BeforeEach
    public void setUp() {
        empresas = new ArrayList<>();
    }

    @Test
    public void testAdicionarEmpresa() {
        List<String> competencias = new ArrayList<>();
        competencias.add("Desenvolvimento Web");
        competencias.add("Machine Learning");
        competencias.add("Banco de Dados");

        Empresa.adicionarEmpresa(empresas, "EmpresaXYZ", "contato@empresa.com", 12345678901234L, "Brasil", "SP", 12345678, "Descrição da empresa", competencias);

        Assertions.assertEquals(1, empresas.size());
        Assertions.assertEquals("EmpresaXYZ", empresas.get(0).getNome());
        Assertions.assertEquals("contato@empresa.com", empresas.get(0).getEmail());
        Assertions.assertEquals(12345678901234L, empresas.get(0).getCnpj());
        Assertions.assertEquals("Brasil", empresas.get(0).getPais());
        Assertions.assertEquals("SP", empresas.get(0).getEstado());
        Assertions.assertEquals(12345678, empresas.get(0).getCep());
        Assertions.assertEquals("Descrição da empresa", empresas.get(0).getDescricao());
        Assertions.assertEquals(competencias, empresas.get(0).getCompetenciasEsperadas());
    }
}