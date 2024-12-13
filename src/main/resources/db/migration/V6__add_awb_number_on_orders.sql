ALTER TABLE orders
    ADD COLUMN awb_number VARCHAR(50);

CREATE INDEX idx_orders_awb_number ON orders (awb_number);