ALTER TABLE pessoa drop column cidade;
ALTER TABLE pessoa drop column cep;
ALTER TABLE pessoa add column cep varchar(9);

