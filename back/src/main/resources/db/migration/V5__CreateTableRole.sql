CREATE TABLE roles (
  id SMALLINT NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE usuario
drop column roles,
ADD COLUMN role SMALLINT;

ALTER TABLE usuario
ADD FOREIGN KEY (role) REFERENCES roles(id);
