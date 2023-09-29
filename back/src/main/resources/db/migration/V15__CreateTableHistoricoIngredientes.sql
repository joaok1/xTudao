CREATE TABLE historico_ingrediente(
  id SMALLINT NOT NULL AUTO_INCREMENT,
  ingrediente Int NOT NULL,
  valor Double NOT NULL,
  data DATE NOT NULL,
  PRIMARY KEY (id),
  FOREIGN KEY (ingrediente) REFERENCES produtos(id)
);