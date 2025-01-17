CREATE TABLE clients(
                      id CHAR(36) NOT NULL,
                      name VARCHAR(100) NOT NULL ,
                      cpf VARCHAR(14) NOT NULL,
                      cnpj VARCHAR(18),
                      address VARCHAR(255) NOT NULL,
                      telephone VARCHAR(20) NOT NULL,
                      email VARCHAR(100) NOT NULL,
                      active TINYINT(1) NOT NULL,

                      PRIMARY KEY(id)
);