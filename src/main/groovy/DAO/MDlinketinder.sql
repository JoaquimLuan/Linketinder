CREATE TABLE "pais" (
  "id" int PRIMARY KEY,
  "nome" varchar
);

CREATE TABLE "competencias" (
  "id" int PRIMARY KEY,
  "nome" varchar
);

CREATE TABLE "empresas" (
  "id" int PRIMARY KEY,
  "nome" varchar,
  "cnpj" varchar,
  "email" varchar,
  "cep" varchar,
  "id_pais" int,
  "vagas" int
);

CREATE TABLE "candidatos" (
  "id" int PRIMARY KEY,
  "nome" varchar,
  "sobrenome" varchar,
  "data_nascimento" date,
  "email" varchar,
  "cpf" varchar,
  "cep" varchar,
  "id_pais" int
);

CREATE TABLE "vagas" (
  "id" int PRIMARY KEY,
  "nome" vagas,
  "descricao" varchar,
  "salario" decimal(8,2)
);

CREATE TABLE "empresa_skills" (
  "id" int PRIMARY KEY,
  "id_empresa" int,
  "id_competencias" int
);

CREATE TABLE "usuario_skills" (
  "id" int PRIMARY KEY,
  "id_candidaos" int,
  "id_competencias" int
);

CREATE TABLE "vagas_skills" (
  "id" int PRIMARY KEY,
  "id_vagas" int,
  "id_competencias" int
);

CREATE TABLE "matches" (
  "id" int PRIMARY KEY,
  "id_empresa" int,
  "id_candidato" int,
  "id_vagas" int,
  "data_match" timestamp
);

ALTER TABLE "empresas" ADD FOREIGN KEY ("id_pais") REFERENCES "pais" ("id");

ALTER TABLE "empresas" ADD FOREIGN KEY ("vagas") REFERENCES "vagas" ("id");

ALTER TABLE "candidatos" ADD FOREIGN KEY ("id_pais") REFERENCES "pais" ("id");

ALTER TABLE "empresa_skills" ADD FOREIGN KEY ("id_empresa") REFERENCES "empresas" ("id");

ALTER TABLE "empresa_skills" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "usuario_skills" ADD FOREIGN KEY ("id_candidaos") REFERENCES "candidatos" ("id");

ALTER TABLE "usuario_skills" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "vagas_skills" ADD FOREIGN KEY ("id_vagas") REFERENCES "vagas" ("id");

ALTER TABLE "vagas_skills" ADD FOREIGN KEY ("id_competencias") REFERENCES "competencias" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("id_empresa") REFERENCES "empresas" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("id_candidato") REFERENCES "candidatos" ("id");

ALTER TABLE "matches" ADD FOREIGN KEY ("id_vagas") REFERENCES "vagas" ("id");
