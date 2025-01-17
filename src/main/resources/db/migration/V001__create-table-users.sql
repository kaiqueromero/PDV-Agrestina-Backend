CREATE TABLE users(
                      id CHAR(36) NOT NULL,
                      name VARCHAR(100) NOT NULL ,
                      login VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL UNIQUE,
                      password VARCHAR(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
                      user_role VARCHAR(100) NOT NULL,
                      active TINYINT(1) NOT NULL,

                      PRIMARY KEY(id)
);