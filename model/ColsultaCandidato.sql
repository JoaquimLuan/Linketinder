SELECT c.id, c.nome, c.sobrenome, c.data_nascimento, c.email, c.cpf, c.cep, p.nome AS nome_pais
FROM candidatos AS c
         INNER JOIN pais AS p ON c.id_pais = p.id;