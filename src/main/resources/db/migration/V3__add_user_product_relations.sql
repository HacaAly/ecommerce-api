--Add user_id column to products table
ALTER TABLE product
ADD COLUMN user_id BIGINT;

-- Add foreign key constraint
ALTER TABLE product
ADD CONSTRAINT fk_product_user
FOREIGN KEY (user_id) REFERENCES users(user_id);

-- Add index on user_id for better performance
CREATE INDEX idx_products_user_id ON product(user_id);