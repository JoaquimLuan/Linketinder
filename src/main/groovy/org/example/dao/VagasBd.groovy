package org.example.dao

import org.example.usuarios.Candidato
import org.example.usuarios.Vaga

import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet

class VagasBd {

    static Scanner teclado = new Scanner(System.in);

    static void listarVagas() {
        String BUSCAR_TODOS = "SELECT v.id, v.nome, v.descricao, v.salario, e.nome AS nome\n" +
                "FROM vagas AS v, empresas AS e\n" +
                "WHERE v.id_empresa = e.id;";

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement vagas = conn.prepareStatement(
                    BUSCAR_TODOS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = vagas.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Listando vagas ------");
                System.out.println("------------------------");
                while (res.next()) {
                    System.out.println("ID: " + res.getInt(1));
                    System.out.println("Nome: " + res.getString(2));
                    System.out.println("Descrição: " + res.getString(3));
                    System.out.println("Salário: " + res.getDouble(4));
                    System.out.println("Empresa: " + res.getString(5));
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("não existe vagas cadastradas");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar toda a lista");
            System.exit(-42);
        }
    }

    static void inserirVaga() {

        List<Vaga> vagas = new ArrayList<>()

        System.out.println("Adicionar vaga:");

        Vaga.adicionarVaga(vagas, teclado);

        String nomeEmpresa = vagas[0].empresa

        int idEmpresa = consultarIdEmpresa(nomeEmpresa)

        if (idEmpresa != -1) {
            String INSERIR_VAGA = "INSERT INTO vagas (nome, descricao, salario, id_empresa) VALUES (?, ?, ?, ?) RETURNING id"

            try {
                Connection conn = Coneccao.conectar()
                PreparedStatement salvarVaga = conn.prepareStatement(INSERIR_VAGA, PreparedStatement.RETURN_GENERATED_KEYS)

                salvarVaga.setString(1, vagas[0].nome)
                salvarVaga.setString(2, vagas[0].descricao)
                salvarVaga.setDouble(3, vagas[0].salario)
                salvarVaga.setInt(4, idEmpresa)

                salvarVaga.executeUpdate()

                ResultSet generatedKeys = salvarVaga.getGeneratedKeys()
                int idVaga = -1
                if (generatedKeys.next()) {
                    idVaga = generatedKeys.getInt(1)
                }

                salvarVaga.close()

                boolean continuar = true

                while (continuar) {
                    continuar = Competencias.adicionarCompetencia(teclado, conn, idVaga, "vaga");
                }

                Coneccao.desconectar(conn)
                System.out.println("A vaga " + vagas[0].nome + " foi inserida com sucesso!")

            } catch (Exception e) {
                e.printStackTrace()
                System.err.println("Erro ao inserir vaga")
                System.exit(-42)
            }
        } else {
            System.err.println("Empresa não encontrada. Certifique-se de que o nome da empresa está correto.")
        }
    }

    static int consultarIdEmpresa(String nomeEmpresa) {

        String CONSULTAR_ID_EMPRESA = "SELECT id FROM empresas WHERE nome = ?"

        try {
            Connection conn = Coneccao.conectar()
            PreparedStatement consulta = conn.prepareStatement(CONSULTAR_ID_EMPRESA)
            consulta.setString(1, nomeEmpresa)
            ResultSet resultado = consulta.executeQuery()

            if (resultado.next()) {
                return resultado.getInt("id")
            } else {
                return -1
            }
        } catch (Exception e) {
            e.printStackTrace()
            System.err.println("Erro ao consultar o ID da empresa")
            System.exit(-42)
            return -1
        }
    }

    static void deletarVaga() {

        String BUSCAR_POR_NOME_VAGA = "SELECT id FROM vagas WHERE nome=?";
        String DELETAR_VAGA = "DELETE FROM vagas WHERE id=?";
        String DELETAR_COMPETENCIAS_VAGA = "DELETE FROM vagas_skills WHERE id_vagas=?";

        System.out.println("Informe o nome da vaga: ");
        String nomeVaga = teclado.nextLine();

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement buscaVaga = conn.prepareStatement(
                    BUSCAR_POR_NOME_VAGA,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            buscaVaga.setString(1, nomeVaga);
            ResultSet res = buscaVaga.executeQuery();

            if (res.next()) {
                int idVaga = res.getInt("id");

                PreparedStatement deletarCompetenciasVaga = conn.prepareStatement(DELETAR_COMPETENCIAS_VAGA);
                deletarCompetenciasVaga.setInt(1, idVaga);
                deletarCompetenciasVaga.executeUpdate();
                deletarCompetenciasVaga.close();

                PreparedStatement deletarVaga = conn.prepareStatement(DELETAR_VAGA);
                deletarVaga.setInt(1, idVaga);
                deletarVaga.executeUpdate();
                deletarVaga.close();

                Coneccao.desconectar(conn);
                System.out.println("Vaga e suas competências foram deletadas com sucesso!!");
            } else {
                System.out.println("Não existe vaga com o nome informado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível deletar a vaga e suas competências");
            System.exit(-42);
        }
    }

}
