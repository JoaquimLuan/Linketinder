CREATE TABLE "pais" (
"id" serial PRIMARY KEY,
"nome" varchar(20) NOT NULL
);

CREATE TABLE "competencias" (
"id" serial PRIMARY KEY,
"nome" varchar(50) NOT NULL
);

CREATE TABLE "empresas" (
"id" serial PRIMARY KEY,
"nome" varchar(20) NOT NULL,
"cnpj" bigint NOT NULL ,
"email" varchar(50) NOT NULL,
"cep" int NOT NULL,
"id_pais" int REFERENCES "pais" ("id") NOT NULL ,
"vagas" int REFERENCES "vagas" ("id")
);

CREATE TABLE "candidatos" (
"id" serial PRIMARY KEY,
"nome" varchar(20) NOT NULL,
"sobrenome" varchar(20) NOT NULL,
"data_nascimento" date NOT NULL,
"email" varchar(50) NOT NULL,
"cpf" bigint NOT NULL,
"cep" int NOT NULL,
"id_pais" int REFERENCES "pais" ("id") NOT NULL
);

CREATE TABLE "vagas" (
"id" serial PRIMARY KEY,
"nome" varchar(20) NOT NULL,
"descricao" varchar(50) NOT NULL,
"salario" decimal(8,2)
);

CREATE TABLE "empresa_skills" (
"id" serial PRIMARY KEY,
"id_empresa" int REFERENCES "empresas" ("id") NOT NULL,
"id_competencias" int REFERENCES "competencias" ("id") NOT NULL
);

CREATE TABLE "usuario_skills" (
"id" serial PRIMARY KEY,
"id_candidatos" int REFERENCES "candidatos" ("id") NOT NULL,
"id_competencias" int REFERENCES "competencias" ("id") NOT NULL
);

CREATE TABLE "vagas_skills" (
"id" serial PRIMARY KEY,
"id_vagas" int REFERENCES "vagas" ("id") NOT NULL,
"id_competencias" int REFERENCES "competencias" ("id") NOT NULL
);

CREATE TABLE "matches" (
"id" serial PRIMARY KEY,
"id_empresa" int REFERENCES "empresas" ("id"),
"id_candidato" int REFERENCES "candidatos" ("id"),
"id_vagas" int REFERENCES "vagas" ("id"),
"data_match" timestamp
);
