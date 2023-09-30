package org.example.dao;

import org.example.dao.Coneccao
import org.example.usuarios.Empresa

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


class EmpresaBd {

    static Scanner teclado = new Scanner(System.in);

    static void listarEmpresas() {
        String BUSCAR_TODOS = "SELECT e.id, e.nome, e.cnpj, e.email, e.cep, p.nome AS nome_pais, e.vagas FROM empresas AS e, pais AS p WHERE e.id_pais = p.id;";

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement empresas = conn.prepareStatement(
                    BUSCAR_TODOS,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            ResultSet res = empresas.executeQuery();

            res.last();
            int qtd = res.getRow();
            res.beforeFirst();

            if (qtd > 0) {
                System.out.println("Listando empresas ------");
                System.out.println("------------------------");
                while (res.next()) {
                    System.out.println("ID: " + res.getInt(1));
                    System.out.println("Nome: " + res.getString(2));
                    System.out.println("CNPJ: " + res.getString(3));
                    System.out.println("Email: " + res.getString(4));
                    System.out.println("CEP: " + res.getString(5));
                    System.out.println("Pais: " + res.getString(6));
                    System.out.println("Vagas: " + res.getString(7));
                    System.out.println("------------------------");
                }
            } else {
                System.out.println("Não existem empresas cadastradas.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao buscar toda a lista");
            System.exit(-42);
        }
    }

    static void inserirEmpresa() {
        List<Empresa> empresas = new ArrayList<>();

        adicionarEmpresas(empresas, teclado);

        try {
            Connection conn = Coneccao.conectar();

            for (Empresa empresa : empresas) {
                int idPais = verificarPais(conn, empresa.getPais());

                if (idPais == -1) {
                    idPais = inserirPais(conn, empresa.getPais());
                }

                int idEmpresa = inserirEmpresa(conn, empresa, idPais);

                boolean continuar = true;

                while (continuar) {
                    System.out.println("Deseja adicionar competência? (S/N): ");
                    String resposta = teclado.nextLine();

                    if (resposta.equalsIgnoreCase("S")) {
                        System.out.println("Informe o nome da competência: ");
                        String nomeCompetencia = teclado.nextLine();

                        int idCompetencias = verificarCompetencia(conn, nomeCompetencia);

                        if (idCompetencias == -1) {
                            idCompetencias = inserirCompetencia(conn, nomeCompetencia);
                        }

                        inserirHabilidadeEmpresa(conn, idEmpresa, idCompetencias);

                        System.out.println("Competência adicionada com sucesso!");
                    } else if (resposta.equalsIgnoreCase("N")) {
                        continuar = false;
                    } else {
                        System.out.println("Opção inválida. Digite 'S' para adicionar competência ou 'N' para finalizar.");
                    }
                }

                Coneccao.desconectar(conn);

                System.out.println("A empresa " + empresa.getNome() + " foi inserida com sucesso!");
            }

            Coneccao.desconectar(conn);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao inserir empresa");
            System.exit(-42);
        }
    }

    static int verificarPais(Connection conn, String nomePais) throws SQLException {
        String verificarPaisSQL = "SELECT id FROM pais WHERE nome = ?";
        PreparedStatement verificarPaisStmt = conn.prepareStatement(verificarPaisSQL);
        verificarPaisStmt.setString(1, nomePais);
        ResultSet paisResultSet = verificarPaisStmt.executeQuery();

        if (paisResultSet.next()) {
            return paisResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirPais(Connection conn, String nomePais) throws SQLException {
        String inserirPaisSQL = "INSERT INTO pais (nome) VALUES (?) RETURNING id";
        PreparedStatement inserirPaisStmt = conn.prepareStatement(inserirPaisSQL);
        inserirPaisStmt.setString(1, nomePais);
        ResultSet novoPaisResultSet = inserirPaisStmt.executeQuery();

        if (novoPaisResultSet.next()) {
            return novoPaisResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirEmpresa(Connection conn, Empresa empresa, int idPais) throws SQLException {
        String inserirEmpresaSQL = "INSERT INTO empresas (nome, cnpj, email, cep, id_pais) VALUES (?, ?, ?, ?, ?) RETURNING id";
        PreparedStatement inserirEmpresaStmt = conn.prepareStatement(inserirEmpresaSQL, PreparedStatement.RETURN_GENERATED_KEYS);
        inserirEmpresaStmt.setString(1, empresa.getNome());
        inserirEmpresaStmt.setLong(2, empresa.getCnpj());
        inserirEmpresaStmt.setString(3, empresa.getEmail());
        inserirEmpresaStmt.setInt(4, empresa.getCep());
        inserirEmpresaStmt.setInt(5, idPais);

        inserirEmpresaStmt.executeUpdate();

        ResultSet generatedKeys = inserirEmpresaStmt.getGeneratedKeys();
        int idEmpresa = -1;
        if (generatedKeys.next()) {
            idEmpresa = generatedKeys.getInt(1);
        }

        inserirEmpresaStmt.close();

        return idEmpresa;
    }

    static int verificarCompetencia(Connection conn, String nomeCompetencia) throws SQLException {
        String verificarCompetenciaSQL = "SELECT id FROM competencias WHERE nome = ?";
        PreparedStatement verificarCompetenciaStmt = conn.prepareStatement(verificarCompetenciaSQL);
        verificarCompetenciaStmt.setString(1, nomeCompetencia);
        ResultSet competenciaResultSet = verificarCompetenciaStmt.executeQuery();

        if (competenciaResultSet.next()) {
            return competenciaResultSet.getInt("id");
        }

        return -1;
    }

    static int inserirCompetencia(Connection conn, String nomeCompetencia) throws SQLException {
        String inserirCompetenciaSQL = "INSERT INTO competencias (nome) VALUES (?) RETURNING id";
        PreparedStatement inserirCompetenciaStmt = conn.prepareStatement(inserirCompetenciaSQL);
        inserirCompetenciaStmt.setString(1, nomeCompetencia);
        ResultSet novaCompetenciaResultSet = inserirCompetenciaStmt.executeQuery();

        if (novaCompetenciaResultSet.next()) {
            return novaCompetenciaResultSet.getInt("id");
        }

        return -1;
    }

    static void inserirHabilidadeEmpresa(Connection conn, int idEmpresa, int idCompetencia) throws SQLException {
        String inserirHabilidadeSQL = "INSERT INTO empresa_skills (id_empresa, id_competencias) VALUES (?, ?)";
        PreparedStatement inserirHabilidadeStmt = conn.prepareStatement(inserirHabilidadeSQL);
        inserirHabilidadeStmt.setInt(1, idEmpresa);
        inserirHabilidadeStmt.setInt(2, idCompetencia);

        inserirHabilidadeStmt.executeUpdate();
        inserirHabilidadeStmt.close();
    }

    static void deletarEmpresa() {

        String BUSCAR_POR_NOME = "SELECT id FROM empresas WHERE nome=?";
        String DELETAR_EMPRESA = "DELETE FROM empresas WHERE id=?";
        String DELETAR_COMPETENCIAS = "DELETE FROM empresa_skills WHERE id_empresa=?";

        System.out.println("Informe o nome da empresa: ");
        String nomeEmpresa = teclado.nextLine();

        try {
            Connection conn = Coneccao.conectar();
            PreparedStatement buscaEmpresa = conn.prepareStatement(
                    BUSCAR_POR_NOME,
                    ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY
            );
            buscaEmpresa.setString(1, nomeEmpresa);
            ResultSet res = buscaEmpresa.executeQuery();

            if (res.next()) {
                int idEmpresa = res.getInt("id");

                PreparedStatement deletarCompetencias = conn.prepareStatement(DELETAR_COMPETENCIAS);
                deletarCompetencias.setInt(1, idEmpresa);
                deletarCompetencias.executeUpdate();
                deletarCompetencias.close();

                PreparedStatement deletarEmpresa = conn.prepareStatement(DELETAR_EMPRESA);
                deletarEmpresa.setInt(1, idEmpresa);
                deletarEmpresa.executeUpdate();
                deletarEmpresa.close();

                Coneccao.desconectar(conn);
                System.out.println("Empresa e suas competências foram deletados com sucesso!!");
            } else {
                System.out.println("Não existe Empresa com o nome informado.");
            }

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Não foi possível deletar a empresa e suas competências");
            System.exit(-42);
        }
    }

    static void adicionarEmpresas(List<Empresa> empresas, Scanner scanner) {
        System.out.println("Quantas empresas deseja adicionar?");
        int quantidade = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Empresa " + (i + 1) + ":");
            Empresa.adicionarEmpresas(empresas, scanner);
        }
    }
}
