package org.example.dao

import org.example.dao.EmpresaBd
import org.example.usuarios.Empresa;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class EmpresaBdTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private EmpresaBd EmpresaBd;
    private List<Empresa> empresas;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        EmpresaBd = new EmpresaBd();
        empresas = new ArrayList<>();
    }

    @Test
    void testInserirEmpresa() throws SQLException {

        Empresa empresa = new Empresa("EmpresaX",12345678911223, "joao@example.com", 12345678, "Brasil");

        when(conn.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        int empresaId = EmpresaBd.inserirEmpresa(conn, empresa, 1);

        verify(preparedStatement, times(1)).executeUpdate();

        verify(preparedStatement, times(1)).getGeneratedKeys();

        verify(resultSet, times(1)).next();

        Assertions.assertEquals(1, empresaId);
    }

}