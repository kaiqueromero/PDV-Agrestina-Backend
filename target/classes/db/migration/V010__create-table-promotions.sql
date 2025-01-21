-- src/main/resources/db/migration/V007__create-table-promotions.sql
CREATE TABLE promotions (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(100) NOT NULL,
                            description VARCHAR(255),
                            discount_percentage DECIMAL(5, 2) NOT NULL,
                            start_date DATE NOT NULL,
                            end_date DATE NOT NULL,
                            active TINYINT(1) NOT NULL,

                            PRIMARY KEY(id)
);