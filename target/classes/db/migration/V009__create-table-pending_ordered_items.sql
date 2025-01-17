CREATE TABLE pending_ordered_items (
                                       id BIGINT NOT NULL AUTO_INCREMENT,
                                       pending_order_id BIGINT NOT NULL,
                                       product_id BIGINT NOT NULL,
                                       quantity INT NOT NULL,
                                       unit_price DECIMAL(10,2) NOT NULL,

                                       PRIMARY KEY(id),
                                       CONSTRAINT PENDING_ORDERED_ITEMS_FK_PRODUCT FOREIGN KEY(product_id) REFERENCES products(id),
                                       CONSTRAINT PENDING_ORDERED_ITEMS_FK_PENDING_ORDER FOREIGN KEY(pending_order_id) REFERENCES pending_orders(id)
);