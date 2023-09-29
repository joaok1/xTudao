CREATE TABLE usuario (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  email VARCHAR(100) UNIQUE NOT NULL,
  senha VARCHAR(255) NOT NULL,
  login VARCHAR(11) UNIQUE NOT NULL,
  roles VARCHAR(100) NOT NULL,
  admin boolean default false,
  data_cadastro TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE pessoa (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  nome VARCHAR(100) NOT NULL,
  sobrenome VARCHAR(100) NOT NULL,
  data_nascimento DATE,
  cpf VARCHAR(11) UNIQUE NOT NULL,
  rg VARCHAR(20),
  endereco VARCHAR(200),
  cidade VARCHAR(100),
  estado VARCHAR(2),
  cep VARCHAR(8),
  telefone VARCHAR(20),
  email VARCHAR(100),
  usuario_id SMALLINT NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (usuario_id) REFERENCES usuario(id)
    ON UPDATE CASCADE
    ON DELETE CASCADE
);
CREATE TABLE usuario_seq (
  next_val BIGINT
);
CREATE TABLE pessoa_seq (
  next_val BIGINT
);

