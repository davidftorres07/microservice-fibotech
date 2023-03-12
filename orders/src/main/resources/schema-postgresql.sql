CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE orders (
  order_id UUID DEFAULT uuid_generate_v4(),
  product_name VARCHAR(255) NOT NULL,
  quantity INTEGER NOT NULL,
  price DECIMAL(10,2) NOT NULL,
  customer_name VARCHAR(255) NOT NULL,
  customer_email VARCHAR(255) NOT NULL,
  shipping_address VARCHAR(255) NOT NULL,
  payment_method VARCHAR(50) NOT NULL,
  payment_details VARCHAR(255) NOT NULL,
  order_date DATE NOT NULL,
  shipping_method VARCHAR(50) NOT NULL,
  shipping_cost DECIMAL(10,2) NOT NULL,
  tax DECIMAL(10,2),
  PRIMARY KEY (order_id)
);