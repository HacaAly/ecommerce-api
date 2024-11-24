-- Create Product table
CREATE TABLE product (
     product_id BIGSERIAL PRIMARY KEY,
     name VARCHAR(255) NOT NULL,
     description TEXT,
     price DECIMAL(10, 2) NOT NULL,
     stock_quantity INT NOT NULL,
     weight DECIMAL(10, 2) NOT NULL DEFAULT 1000.0,
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
     updated_at TIMESTAMP NULL
);

-- Create Category table
CREATE TABLE category (
    category_id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL
);

-- Create Product_Category join table
CREATE TABLE product_category (
    product_id BIGINT,
    category_id BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NULL,
    PRIMARY KEY (product_id, category_id),
    FOREIGN KEY (product_id) REFERENCES product(product_id),
    FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- Add indexes
CREATE INDEX idx_product_name ON product(name);
CREATE INDEX idx_category_name ON category(name);