CREATE TABLE empresa_matriz (
	id INT NOT NULL  AUTO_INCREMENT,
	nome varchar(500),
	endereço varchar(500),
    cep varchar(500),
    municipio varchar(100),
    estado varchar(2),
    sede boolean default true,
	Primary Key (id)
);

CREATE TABLE empresa_filial (
	id INT NOT NULL AUTO_INCREMENT,
	nome VARCHAR(500),
	endereço VARCHAR(500),
    cep VARCHAR(500),
    municipio VARCHAR(100),
    estado VARCHAR(2),
    filial BOOLEAN DEFAULT TRUE,
    id_empresa_matriz INT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fkb_empresa_matriz FOREIGN KEY (id_empresa_matriz) REFERENCES empresa_matriz(id)
);




