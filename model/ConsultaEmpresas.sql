SELECT e.id, e.nome, e.cnpj, e.email, e.cep, p.nome AS nome_pais
FROM empresas AS e
         INNER JOIN pais AS p ON e.id_pais = p.id;