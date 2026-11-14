# Retail Inventory Management System

## 1. Database Normalization

The database was organized using the following normalization forms:

### 1NF
- Each column contains a single value.
- No repeating groups are used.

### 2NF
- The database is in 1NF.
- In the `inventory` table, `sku` and `warehouse_id` form the composite primary key.
- `quantity` and `reorder_level` depend on the complete composite key.

### 3NF
- The database is in 2NF.
- Supplier, category, and warehouse details are stored in separate tables to avoid repeating the same information in product and inventory records.

## 2. Database and Table Creation

Database name: `retail_inventory`

Tables created:
1. `suppliers`
2. `categories`
3. `warehouses`
4. `products`
5. `inventory`

### Table structure

| Table | Columns | Keys and constraints |
|---|---|---|
| `suppliers` | `supplier_id`, `supplier_name`, `supplier_phone` | `supplier_id` primary key; name and phone NOT NULL |
| `categories` | `category_id`, `category_name` | `category_id` primary key; category name UNIQUE and NOT NULL |
| `warehouses` | `warehouse_id`, `warehouse_name` | `warehouse_id` primary key; warehouse name UNIQUE and NOT NULL |
| `products` | `sku`, `product_name`, `category_id`, `supplier_id` | `sku` primary key; `category_id` and `supplier_id` foreign keys |
| `inventory` | `sku`, `warehouse_id`, `quantity`, `reorder_level` | Composite primary key (`sku`, `warehouse_id`); both columns are foreign keys; quantity and reorder level have non-negative CHECK constraints |

### Relationships

- One supplier can supply multiple products.
- One category can contain multiple products.
- One product can have inventory in multiple warehouses.
- One warehouse can store multiple products.

## 3. Data Insertion

Inserted 10 sample records into each of the five tables.

- **Suppliers:** 10 supplier records.
- **Categories:** 10 product categories.
- **Warehouses:** 10 warehouse records.
- **Products:** 10 product records.
- **Inventory:** 10 stock records.

The parent tables (`suppliers`, `categories`, and `warehouses`) were populated before the dependent tables (`products` and `inventory`) to satisfy foreign key requirements.

## 4. Constraint Testing

The following invalid entries were tested individually to verify the constraints.

### Test 1: Invalid foreign key

```sql
INSERT INTO products (sku, product_name, category_id, supplier_id)
VALUES ('P111', 'Tablet', 99, 1);
```

**Expected/observed result:** Error 1452 — the referenced category ID does not exist.

### Test 2: Duplicate primary key

```sql
INSERT INTO suppliers (supplier_id, supplier_name, supplier_phone)
VALUES (1, 'Duplicate Supplier', '9000000000');
```

**Expected/observed result:** Error 1062 — supplier ID `1` already exists.

### Test 3: Duplicate UNIQUE value

```sql
INSERT INTO categories (category_id, category_name)
VALUES (11, 'Electronics');
```

**Expected/observed result:** Error 1062 — category name `Electronics` already exists.

### Test 4: Negative inventory quantity

```sql
INSERT INTO inventory (sku, warehouse_id, quantity, reorder_level)
VALUES ('P110', 9, -5, 2);
```

**Expected/observed result:** CHECK constraint violation — quantity cannot be negative.

## 5. UPDATE Operation

Updated the phone number of supplier ID `1`.

```sql
UPDATE suppliers
SET supplier_phone = '9001234567'
WHERE supplier_id = 1;
```

Verified the updated record:

```sql
SELECT *
FROM suppliers
WHERE supplier_id = 1;
```

**Result:** The phone number was updated to `9001234567`.

## 6. DELETE Operation

Deleted product `P110` and its related inventory record. The inventory record was deleted first because it references the product through a foreign key.

### Delete inventory record

```sql
DELETE FROM inventory
WHERE sku = 'P110';
```

### Delete product record

```sql
DELETE FROM products
WHERE sku = 'P110';
```

### Verify deletion

```sql
SELECT *
FROM products
WHERE sku = 'P110';

SELECT *
FROM inventory
WHERE sku = 'P110';
```

**Result:** Both queries returned zero matching records.

## 7. MySQL System Database Exploration

Explored the following MySQL system databases.

### `information_schema`

```sql
SELECT TABLE_NAME
FROM information_schema.TABLES
WHERE TABLE_SCHEMA = 'retail_inventory';
```

Used to view the tables in the project database.

### `mysql`

```sql
SHOW TABLES FROM mysql;
```

Explored the tables related to MySQL accounts and privileges.

### `performance_schema`

```sql
SHOW TABLES FROM performance_schema;
```

Explored the tables used for monitoring MySQL server activity and performance.

### `sys`

```sql
SHOW TABLES FROM sys;
```

Explored the views and tables that make MySQL performance information easier to inspect.

## 8. Final Record Count Verification

```sql
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
```

Expected counts after the UPDATE and DELETE operations:

| Table | Final record count |
|---|---:|
| `suppliers` | 10 |
| `categories` | 10 |
| `warehouses` | 10 |
| `products` | 9 |
| `inventory` | 9 |
