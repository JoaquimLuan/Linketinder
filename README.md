# Linketinder

Seja Bem Vindo!!!

Está aplicação será desenvolvida ao longo do programa Acelera ZG!!!

No momento a mesma está na versão 1.0.2. E nesta versão já possui integração com o Banco de Dados Postgresql.

Nesta versão é possui um menu para usuário com as seguinte opções:

![Seleção_787](https://github.com/JoaquimLuan/Linketinder/assets/109047479/ae128a79-be1f-4c8c-ab1a-5e3eaa2cbfd2)



Onde utilizando o console o usuário pode utilizar o menu para adicionar uma nova empresa, 
adicionar um novo candidato e listar as empresesa e os candidatos que já estão cadastrados.

Digiando o numero a qual a opção desejada esta a frente.

Foi introduzio da modelagem do banco de dados, o mesmo foi realizado na plataforma https://dbdiagram.io/

Onde foram criados as tabelas pais, competencias, empresas, candidatos, vagas, vagas_skills, empresa_skills, usuario_skills e matches:

![linketinder](https://github.com/JoaquimLuan/Linketinder/assets/109047479/ac1d87b3-8275-4983-aea1-2ccd26692174)



Foi utilizado o banco de dados Postegresql

Nessa nova etapa foi realizado a refatoração da aplicação utilizando os metodos de Clin code.

Foram realizadas as seguinte alterações:

- Criação da classe Regex, onde todos os regex e funções relacionadas e a regex foram tranferidas para esta classe.
- Criação da classe ValidacaoDeData, onde a logia de validação de datas foi tranferida para ela, sendo possivel utilizar essas valições em outras partes do código caso necessário, sem a necessidade de repetir o código.
- Criado a classe Vaga pois ate o momento não havia sido criado a logica de adição de vaga no Back End.

No pacote de DAO:

- Foi criado a classe Competencias, onde toda a logica de validação e inserção de competências foi tranferida para esta classe.
- Foi criado a classe País, onde toda a logica de validação e inserção de país foi tranferida para esta classe.
- Foi criado a classe Coneccao, onde toda a logica de conecção com banco de dados e execução de comandos SQL, foi tranferida para esta classe.
- Foi refatorado todas as funções colocando cada uma para executar apenas uma função, e foi retirado todas as duplicidade de codigos com os desmenbramentos de codigos para outras classes.


