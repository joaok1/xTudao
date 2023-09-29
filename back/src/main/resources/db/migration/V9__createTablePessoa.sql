ALTER TABLE pessoa
ADD COLUMN id_empresa_matriz INT ,
ADD COLUMN id_empresa_filial INT ,
ADD FOREIGN KEY (id_empresa_matriz) REFERENCES empresa_matriz(id),
ADD FOREIGN KEY (id_empresa_filial) REFERENCES empresa_filial(id);
