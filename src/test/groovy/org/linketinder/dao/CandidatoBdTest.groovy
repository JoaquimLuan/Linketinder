package org.linketinder.dao

import org.linketinder.Model.CandidatoModel
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class CandidatoBdTest {

    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    private CandidatoDao candidatoBd;
    private List<CandidatoModel> candidatos;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        candidatoBd = new CandidatoDao();
        candidatos = new ArrayList<>();
    }

    @Test
    void testInserirCandidato() throws SQLException {

        CandidatoModel candidato = new CandidatoModel("João", "Sobrenome", "11-11-2000", "joao@example.com", "12345678901L", "123456", "Brasil");

        when(conn.prepareStatement(anyString(), eq(PreparedStatement.RETURN_GENERATED_KEYS))).thenReturn(preparedStatement);
        when(preparedStatement.executeUpdate()).thenReturn(1);
        when(preparedStatement.getGeneratedKeys()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(1)).thenReturn(1);

        int candidatoId = candidatoBd.inserir(conn, candidato, 1);

        verify(preparedStatement, times(1)).executeUpdate();

        verify(preparedStatement, times(1)).getGeneratedKeys();

        verify(resultSet, times(1)).next();

        Assertions.assertEquals(1, candidatoId);
    }

}
