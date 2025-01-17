CREATE TABLE ordered_items (
                              id BIGINT NOT NULL AUTO_INCREMENT,
                              order_id BIGINT NOT NULL,
                              product_id BIGINT NOT NULL,
                              quantity INT NOT NULL,
                              unit_price DECIMAL(10,2) NOT NULL,

                              PRIMARY KEY(id),
                              CONSTRAINT ORDERED_ITEMS_FK_PRODUCT FOREIGN KEY(product_id) REFERENCES products(id),
                              CONSTRAINT ORDERED_ITEMS_FK_ORDER FOREIGN KEY(order_id) REFERENCES orders(id)
);