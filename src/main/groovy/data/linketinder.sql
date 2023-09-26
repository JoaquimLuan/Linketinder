CREATE TABLE "pais" (
  "id" serial PRIMARY KEY,
  "nome" varchar(50) NOT NULL
);

CREATE TABLE "competencias" (
  "id" serial PRIMARY KEY,
  "nome" varchar(50) NOT NULL
);

CREATE TABLE "empresas" (
  "id" serial PRIMARY KEY,
  "nome" varchar(50) NOT NULL,
  "cnpj" varchar(14) NOT NULL,
  "email" varchar(50) NOT NULL,
  "cep" varchar(10) NOT NULL,
  "id_pais" int NOT null,
  "vagas" int NOT null
);

CREATE TABLE "candidatos" (
  "id" serial PRIMARY KEY,
  "nome" varchar(50) NOT NULL,
  "sobrenome" varchar(50) NOT NULL,
  "data_nascimento" date NOT NULL,
  "email" varchar(50) NOT NULL,
  "cpf" varchar(11) NOT NULL,
  "cep" varchar,(10) NOT NULL,
  "id_pais" int NOT NULL
);

CREATE TABLE "vagas" (
  "id" serial PRIMARY KEY,
  "nome" varchar(50) NOT NULL,
  "descricao" varchar(50) NOT NULL,
  "salario" decimal(8,2) NOT NULL,
  "id_competencias" int NOT NULL 
);

CREATE TABLE "empresa_skills" (
  "id" serial PRIMARY KEY,
  "id_empresa" int NOT NULL,
  "id_competencias" int NOT NULL
);

CREATE TABLE "usuario_skills" (
  "id" serial PRIMARY KEY,
  "id_candidaos" int NOT NULL,
  "id_competencias" int NOT NULL
);

CREATE TABLE "matches" (
  "id" serial PRIMARY KEY,
  "id_empresa" int NOT NULL,
  "id_candidato" int NOT NULL,
  "data_match" date NOT NULL
);

ALTER TABLE "empresas" ADD FOREIGN KEY ("id_pais") REFERENCES "pais" ("id");

ALTER TABLE "empresas" ADD FOREIGN KEY ("vagas") REFERENCES "vagas" ("id");

ALTER TABLE "candidatos" ADD FOREIGN KEY ("id_pais") REFERENCES "pais" ("id");

ALTER TABLE "vagas" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "empresa_skills" ADD FOREIGN KEY ("id_empresa") REFERENCES "empresas" ("id");

ALTER TABLE "empresa_skills" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "usuario_skills" ADD FOREIGN KEY ("id_candidaos") REFERENCES "candidatos" ("id");

ALTER TABLE "usuario_skills" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("id_empresa") REFERENCES "empresas" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("id_candidato") REFERENCES "candidatos" ("id");
