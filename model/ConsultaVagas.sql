SELECT v.id, v.nome, v.descricao, v.salario, e.nome AS nome
    FROM vagas AS v
    INNER JOIN empresas AS e ON v.id_empresa = e.id;