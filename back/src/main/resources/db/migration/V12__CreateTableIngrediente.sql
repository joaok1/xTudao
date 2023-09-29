CREATE TABLE produtos (
	id INT NOT NULL  AUTO_INCREMENT,
	nome varchar(50) NOT NULL,
	fator_medida_peso varchar(3) NOT NULL,
    porcao DOUBLE NOT NULL,
    valor double NOT NULL,
    quantidade DOUBLE NOT NULL,
	Primary Key (id)
);

CREATE TABLE lanche(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  nome varchar(50) NOT NULL,
  valor DOUBLE NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE list_lanche_ingrediente(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  lanche_id SMALLINT NOT NULL,
  ingrediente Int NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (ingrediente) REFERENCES produtos(id),
  FOREIGN KEY (lanche_id) REFERENCES lanche(id)
);
