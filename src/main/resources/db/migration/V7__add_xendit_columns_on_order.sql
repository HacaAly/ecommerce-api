ALTER TABLE orders
ADD COLUMN xendit_invoice_id VARCHAR(255),
ADD COLUMN xendit_payment_method VARCHAR(50),
ADD COLUMN xendit_payment_status VARCHAR(50);
