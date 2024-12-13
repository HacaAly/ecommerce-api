CREATE TABLE user_addresses (
    user_adress_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    address_name VARCHAR(100) NOT NULL,
    street_address VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    postal_code VARCHAR(20) NOT NULL,
    country VARCHAR(100) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_user_addresses_users FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE orders (
    order_id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    subtotal DECIMAL(10, 2) NOT NULL,
    shipping_fee DECIMAL(10, 2) NOT NULL,
    tax_fee DECIMAL(10, 2) NOT NULL,
    total_amount DECIMAL(10, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    order_date TIMESTAMP NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_items (
    order_item_id SERIAL PRIMARY KEY,
    order_id INTEGER NOT NULL,
    product_id INTEGER NOT NULL,
    user_address_id INTEGER NOT NULL,
    quantity INTEGER NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_order_items_orders FOREIGN KEY (order_id) REFERENCES orders (order_id),
    CONSTRAINT fk_order_items_products FOREIGN KEY (product_id ) REFERENCES product (product_id),
    CONSTRAINT fk_order_items_user_addresses FOREIGN KEY (user_address_id) REFERENCES user_addresses (user_adress_id)
);


-- create indexes for foreign key
CREATE INDEX idx_user_addresses_users ON user_addresses(user_id);
CREATE INDEX idx_orders_users ON orders (user_id);
CREATE INDEX idx_order_items_orders ON order_items (order_id);
CREATE INDEX idx_order_items_products ON order_items (product_id);
CREATE INDEX idx_order_items_user_addresses ON order_items (user_address_id);



