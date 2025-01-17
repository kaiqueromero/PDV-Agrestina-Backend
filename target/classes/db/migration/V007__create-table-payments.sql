CREATE TABLE payments (
                          id char(36) NOT NULL,
                          order_id BIGINT NOT NULL,
                          method VARCHAR(50) NOT NULL,
                          payment_date DATE,
                          receipt VARCHAR(255),

                          PRIMARY KEY(id),
                          CONSTRAINT PAYMENTS_FK_ORDER FOREIGN KEY(order_id) REFERENCES orders(id)
);
CREATE INDEX idx_order_id ON payments(order_id);