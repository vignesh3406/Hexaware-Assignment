
-- =====================================================
-- RETAIL INVENTORY MANAGEMENT SYSTEM
-- Database: retail_inventory
-- Tool: MySQL Workbench
-- =====================================================

-- 1. CREATE DATABASE
CREATE DATABASE retail_inventory;
USE retail_inventory;


-- 2. CREATE SUPPLIERS TABLE
CREATE TABLE suppliers (
    supplier_id INT AUTO_INCREMENT,
    supplier_name VARCHAR(100) NOT NULL,
    supplier_phone VARCHAR(15) NOT NULL,
    PRIMARY KEY (supplier_id)
);


-- 3. CREATE CATEGORIES TABLE
CREATE TABLE categories (
    category_id INT AUTO_INCREMENT,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (category_id)
);


-- 4. CREATE WAREHOUSES TABLE
CREATE TABLE warehouses (
    warehouse_id INT AUTO_INCREMENT,
    warehouse_name VARCHAR(100) NOT NULL UNIQUE,
    PRIMARY KEY (warehouse_id)
);


-- 5. CREATE PRODUCTS TABLE
CREATE TABLE products (
    sku VARCHAR(20),
    product_name VARCHAR(100) NOT NULL,
    category_id INT NOT NULL,
    supplier_id INT NOT NULL,
    PRIMARY KEY (sku),

    CONSTRAINT fk_products_categories
        FOREIGN KEY (category_id)
        REFERENCES categories(category_id),

    CONSTRAINT fk_products_suppliers
        FOREIGN KEY (supplier_id)
        REFERENCES suppliers(supplier_id)
);


-- 6. CREATE INVENTORY TABLE
CREATE TABLE inventory (
    sku VARCHAR(20) NOT NULL,
    warehouse_id INT NOT NULL,
    quantity INT NOT NULL DEFAULT 0,
    reorder_level INT NOT NULL DEFAULT 0,

    PRIMARY KEY (sku, warehouse_id),

    CONSTRAINT fk_inventory_products
        FOREIGN KEY (sku)
        REFERENCES products(sku),

    CONSTRAINT fk_inventory_warehouses
        FOREIGN KEY (warehouse_id)
        REFERENCES warehouses(warehouse_id),

    CONSTRAINT chk_quantity
        CHECK (quantity >= 0),

    CONSTRAINT chk_reorder_level
        CHECK (reorder_level >= 0)
);


-- =====================================================
-- 7. INSERT SAMPLE DATA
-- =====================================================

-- Suppliers
INSERT INTO suppliers
(supplier_id, supplier_name, supplier_phone)
VALUES
(1, 'ABC Traders', '9876543210'),
(2, 'XYZ Suppliers', '9876543211'),
(3, 'Global Electronics', '9876543212'),
(4, 'Tech World', '9876543213'),
(5, 'Smart Distributors', '9876543214'),
(6, 'Prime Supplies', '9876543215'),
(7, 'City Wholesale', '9876543216'),
(8, 'Universal Traders', '9876543217'),
(9, 'Digital Hub', '9876543218'),
(10, 'Metro Suppliers', '9876543219');


-- Categories
INSERT INTO categories
(category_id, category_name)
VALUES
(1, 'Electronics'),
(2, 'Accessories'),
(3, 'Furniture'),
(4, 'Stationery'),
(5, 'Clothing'),
(6, 'Home Appliances'),
(7, 'Sports'),
(8, 'Grocery'),
(9, 'Toys'),
(10, 'Footwear');


-- Warehouses
INSERT INTO warehouses
(warehouse_id, warehouse_name)
VALUES
(1, 'Chennai Warehouse'),
(2, 'Bangalore Warehouse'),
(3, 'Mumbai Warehouse'),
(4, 'Hyderabad Warehouse'),
(5, 'Delhi Warehouse'),
(6, 'Pune Warehouse'),
(7, 'Coimbatore Warehouse'),
(8, 'Kochi Warehouse'),
(9, 'Kolkata Warehouse'),
(10, 'Ahmedabad Warehouse');


-- Products
INSERT INTO products
(sku, product_name, category_id, supplier_id)
VALUES
('P101', 'Laptop', 1, 1),
('P102', 'Mouse', 2, 2),
('P103', 'Keyboard', 2, 3),
('P104', 'Monitor', 1, 4),
('P105', 'Office Chair', 3, 5),
('P106', 'Notebook', 4, 6),
('P107', 'T-Shirt', 5, 7),
('P108', 'Refrigerator', 6, 8),
('P109', 'Football', 7, 9),
('P110', 'Toy Car', 9, 10);


-- Inventory
INSERT INTO inventory
(sku, warehouse_id, quantity, reorder_level)
VALUES
('P101', 1, 20, 5),
('P101', 2, 10, 5),
('P102', 1, 50, 10),
('P103', 2, 30, 5),
('P104', 3, 15, 3),
('P105', 4, 12, 4),
('P106', 5, 100, 20),
('P107', 6, 40, 10),
('P108', 7, 8, 2),
('P109', 8, 25, 5);


-- =====================================================
-- 8. CONSTRAINT TESTING
-- Run these statements individually when testing.
-- They are intentionally commented out so the main
-- script can be executed without constraint errors.
-- =====================================================

-- Test 1: Duplicate primary key
-- INSERT INTO suppliers
-- (supplier_id, supplier_name, supplier_phone)
-- VALUES (1, 'Duplicate Supplier', '9000000000');


-- Test 2: Duplicate UNIQUE category name
-- INSERT INTO categories
-- (category_id, category_name)
-- VALUES (11, 'Electronics');


-- Test 3: Invalid foreign key
-- INSERT INTO products
-- (sku, product_name, category_id, supplier_id)
-- VALUES ('P111', 'Tablet', 99, 1);


-- Test 4: Negative inventory quantity
-- INSERT INTO inventory
-- (sku, warehouse_id, quantity, reorder_level)
-- VALUES ('P110', 9, -5, 2);


-- =====================================================
-- 9. UPDATE OPERATION
-- =====================================================

UPDATE suppliers
SET supplier_phone = '9001234567'
WHERE supplier_id = 1;


-- Verify updated supplier
SELECT *
FROM suppliers
WHERE supplier_id = 1;


-- =====================================================
-- 10. DELETE OPERATION
-- =====================================================

-- Delete related inventory record first
DELETE FROM inventory
WHERE sku = 'P110';

-- Delete discontinued product
DELETE FROM products
WHERE sku = 'P110';


-- Verify deletion
SELECT *
FROM products
WHERE sku = 'P110';

SELECT *
FROM inventory
WHERE sku = 'P110';


-- =====================================================
-- 11. FINAL RECORD COUNT
-- =====================================================

SELECT 'suppliers' AS table_name, COUNT(*) AS total_records
FROM suppliers

UNION ALL

SELECT 'categories', COUNT(*) FROM categories

UNION ALL

SELECT 'warehouses', COUNT(*) FROM warehouses

UNION ALL

SELECT 'products', COUNT(*) FROM products

UNION ALL

SELECT 'inventory', COUNT(*) FROM inventory;


-- =====================================================
-- 12. MYSQL SYSTEM DATABASE EXPLORATION
-- =====================================================

-- information_schema
SELECT TABLE_NAME
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'retail_inventory';


-- mysql
SHOW TABLES FROM mysql;


-- performance_schema
SHOW TABLES FROM performance_schema;


-- sys
SHOW TABLES FROM sys;



