CREATE TABLE orders (
    id BIGINT NOT NULL AUTO_INCREMENT,
    date DATE NOT NULL,
    user_id CHAR(36) NOT NULL,
    client_id CHAR(36) NOT NULL,
    status VARCHAR(20) NOT NULL,
    total_value DECIMAL(12, 2) NOT NULL,

    PRIMARY KEY(id),
    CONSTRAINT ORDERS_FK_USER FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT ORDERS_FK_CLIENT FOREIGN KEY(client_id) REFERENCES clients(id)
);
CREATE INDEX idx_date ON orders(date);
