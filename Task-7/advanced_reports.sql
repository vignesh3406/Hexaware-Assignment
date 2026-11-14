-- ============================================================
-- Set 2 - Bank Loan Portfolio
-- Database: bank_loan_portfolio
-- MySQL / MySQL Workbench
--
-- This file creates a sample database, inserts demonstration
-- data, and answers 10 reporting questions from the assignment.
-- The assignment did not provide a schema, so this schema and
-- sample data are designed from the business scenario.
-- Run once on a fresh MySQL server/schema.
-- ============================================================

CREATE DATABASE bank_loan_portfolio;
USE bank_loan_portfolio;

-- 1. TABLE CREATION

CREATE TABLE customers (
    customer_id INT PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20) UNIQUE,
    email VARCHAR(100) UNIQUE
);

CREATE TABLE branches (
    branch_id INT PRIMARY KEY AUTO_INCREMENT,
    branch_name VARCHAR(100) NOT NULL UNIQUE,
    city VARCHAR(60) NOT NULL
);

CREATE TABLE loans (
    loan_id INT PRIMARY KEY AUTO_INCREMENT,
    customer_id INT NOT NULL,
    branch_id INT NOT NULL,
    loan_amount DECIMAL(12,2) NOT NULL,
    interest_rate DECIMAL(5,2) NOT NULL,
    start_date DATE NOT NULL,
    tenure_months INT NOT NULL,
    loan_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE',
    CONSTRAINT chk_loan_amount CHECK (loan_amount > 0),
    CONSTRAINT chk_interest_rate CHECK (interest_rate >= 0),
    CONSTRAINT chk_tenure CHECK (tenure_months > 0),
    CONSTRAINT fk_loans_customer
        FOREIGN KEY (customer_id) REFERENCES customers(customer_id),
    CONSTRAINT fk_loans_branch
        FOREIGN KEY (branch_id) REFERENCES branches(branch_id)
);

-- Each row represents one scheduled instalment.
-- paid_date and amount_paid remain NULL until payment is made.
CREATE TABLE repayments (
    repayment_id INT PRIMARY KEY AUTO_INCREMENT,
    loan_id INT NOT NULL,
    installment_no INT NOT NULL,
    due_date DATE NOT NULL,
    amount_due DECIMAL(10,2) NOT NULL,
    paid_date DATE NULL,
    amount_paid DECIMAL(10,2) NULL,
    CONSTRAINT uq_loan_installment UNIQUE (loan_id, installment_no),
    CONSTRAINT chk_amount_due CHECK (amount_due > 0),
    CONSTRAINT chk_amount_paid CHECK (amount_paid IS NULL OR amount_paid >= 0),
    CONSTRAINT fk_repayments_loan
        FOREIGN KEY (loan_id) REFERENCES loans(loan_id)
);

-- A customer can guarantee a loan. The guarantor is also a customer.
CREATE TABLE loan_guarantors (
    loan_id INT NOT NULL,
    guarantor_customer_id INT NOT NULL,
    guarantee_date DATE NOT NULL,
    PRIMARY KEY (loan_id, guarantor_customer_id),
    CONSTRAINT fk_guarantors_loan
        FOREIGN KEY (loan_id) REFERENCES loans(loan_id),
    CONSTRAINT fk_guarantors_customer
        FOREIGN KEY (guarantor_customer_id) REFERENCES customers(customer_id)
);

-- 2. SAMPLE DATA

INSERT INTO customers (customer_id, first_name, last_name, phone, email) VALUES
(1, 'Arun', 'Kumar', '9000000001', 'arun@example.com'),
(2, 'Priya', 'Sharma', '9000000002', 'priya@example.com'),
(3, 'Rahul', 'Verma', '9000000003', 'rahul@example.com'),
(4, 'Meena', 'Ravi', '9000000004', 'meena@example.com'),
(5, 'Karthik', 'Raj', '9000000005', 'karthik@example.com'),
(6, 'Divya', 'Nair', '9000000006', 'divya@example.com'),
(7, 'Sanjay', 'Das', '9000000007', 'sanjay@example.com'),
(8, 'Anita', 'Roy', '9000000008', 'anita@example.com'),
(9, 'Vikram', 'Singh', '9000000009', 'vikram@example.com'),
(10, 'Lakshmi', 'Iyer', '9000000010', 'lakshmi@example.com');

INSERT INTO branches (branch_id, branch_name, city) VALUES
(1, 'Chennai Main Branch', 'Chennai'),
(2, 'Bangalore City Branch', 'Bangalore'),
(3, 'Coimbatore Branch', 'Coimbatore'),
(4, 'Madurai Branch', 'Madurai'),
(5, 'Kochi Branch', 'Kochi');

INSERT INTO loans
(loan_id, customer_id, branch_id, loan_amount, interest_rate, start_date, tenure_months, loan_status)
VALUES
(101, 1, 1, 500000.00, 8.50, DATE_SUB(CURDATE(), INTERVAL 18 MONTH), 36, 'ACTIVE'),
(102, 1, 2, 250000.00, 9.00, DATE_SUB(CURDATE(), INTERVAL 12 MONTH), 24, 'ACTIVE'),
(103, 2, 1, 300000.00, 8.75, DATE_SUB(CURDATE(), INTERVAL 15 MONTH), 36, 'ACTIVE'),
(104, 3, 1, 150000.00, 10.00, DATE_SUB(CURDATE(), INTERVAL 10 MONTH), 24, 'ACTIVE'),
(105, 4, 2, 700000.00, 8.25, DATE_SUB(CURDATE(), INTERVAL 20 MONTH), 48, 'ACTIVE'),
(106, 5, 2, 200000.00, 9.50, DATE_SUB(CURDATE(), INTERVAL 8 MONTH), 24, 'ACTIVE'),
(107, 6, 3, 900000.00, 7.90, DATE_SUB(CURDATE(), INTERVAL 14 MONTH), 60, 'ACTIVE'),
(108, 7, 3, 400000.00, 9.25, DATE_SUB(CURDATE(), INTERVAL 11 MONTH), 36, 'ACTIVE'),
(109, 8, 4, 100000.00, 11.00, DATE_SUB(CURDATE(), INTERVAL 6 MONTH), 12, 'ACTIVE'),
(110, 9, 1, 650000.00, 8.40, DATE_SUB(CURDATE(), INTERVAL 9 MONTH), 36, 'ACTIVE');

-- Installments for loan 101: four unpaid installments overdue.
INSERT INTO repayments
(loan_id, installment_no, due_date, amount_due, paid_date, amount_paid)
VALUES
(101, 1, DATE_SUB(CURDATE(), INTERVAL 150 DAY), 15000.00, NULL, NULL),
(101, 2, DATE_SUB(CURDATE(), INTERVAL 120 DAY), 15000.00, NULL, NULL),
(101, 3, DATE_SUB(CURDATE(), INTERVAL 90 DAY), 15000.00, NULL, NULL),
(101, 4, DATE_SUB(CURDATE(), INTERVAL 60 DAY), 15000.00, NULL, NULL),
(101, 5, DATE_SUB(CURDATE(), INTERVAL 30 DAY), 15000.00, CURDATE(), 15000.00),
(102, 1, DATE_SUB(CURDATE(), INTERVAL 30 DAY), 12000.00, CURDATE(), 12000.00),
(103, 1, DATE_SUB(CURDATE(), INTERVAL 20 DAY), 10000.00, CURDATE(), 10000.00),
(104, 1, DATE_SUB(CURDATE(), INTERVAL 15 DAY), 7000.00, NULL, NULL),
(105, 1, DATE_SUB(CURDATE(), INTERVAL 100 DAY), 20000.00, NULL, NULL),
(105, 2, DATE_SUB(CURDATE(), INTERVAL 70 DAY), 20000.00, NULL, NULL),
(106, 1, DATE_SUB(CURDATE(), INTERVAL 10 DAY), 9000.00, CURDATE(), 9000.00),
(107, 1, DATE_SUB(CURDATE(), INTERVAL 25 DAY), 18000.00, CURDATE(), 18000.00),
(108, 1, DATE_SUB(CURDATE(), INTERVAL 75 DAY), 13000.00, NULL, NULL),
(109, 1, DATE_SUB(CURDATE(), INTERVAL 5 DAY), 9000.00, CURDATE(), 9000.00),
(110, 1, DATE_SUB(CURDATE(), INTERVAL 80 DAY), 16000.00, NULL, NULL);

INSERT INTO loan_guarantors (loan_id, guarantor_customer_id, guarantee_date) VALUES
(101, 2, CURDATE()),
(102, 3, CURDATE()),
(103, 1, CURDATE()),
(105, 6, CURDATE()),
(107, 4, CURDATE()),
(110, 5, CURDATE());

-- ============================================================
-- 3. TEN REPORTING QUESTIONS
-- ============================================================

-- Q1. Find loans overdue by more than three installments.
-- A scheduled installment is overdue when due_date is before
-- today and paid_date is NULL.
SELECT
    l.loan_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    COUNT(r.repayment_id) AS overdue_installments
FROM loans l
JOIN customers c ON c.customer_id = l.customer_id
JOIN repayments r ON r.loan_id = l.loan_id
WHERE r.due_date < CURDATE()
  AND r.paid_date IS NULL
GROUP BY l.loan_id, c.first_name, c.last_name
HAVING COUNT(r.repayment_id) > 3;

-- Q2. Find customers who have loans in more than one branch.
SELECT
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    COUNT(DISTINCT l.branch_id) AS branch_count
FROM customers c
JOIN loans l ON l.customer_id = c.customer_id
GROUP BY c.customer_id, c.first_name, c.last_name
HAVING COUNT(DISTINCT l.branch_id) > 1;

-- Q3. Find guarantors who are also borrowers.
-- Self-join: loan_guarantors identifies the guarantor customer;
-- loans identifies customers who have borrowed.
SELECT DISTINCT
    g.customer_id AS guarantor_customer_id,
    CONCAT(g.first_name, ' ', g.last_name) AS guarantor_name
FROM loan_guarantors lg
JOIN customers g ON g.customer_id = lg.guarantor_customer_id
JOIN loans borrower_loan ON borrower_loan.customer_id = g.customer_id;

-- Q4. Find branches that do not have any loans.
SELECT
    b.branch_id,
    b.branch_name,
    b.city
FROM branches b
LEFT JOIN loans l ON l.branch_id = b.branch_id
WHERE l.loan_id IS NULL;

-- Q5. CORRELATED SUBQUERY:
-- Find customers whose total loan exposure within a branch is
-- greater than the average customer loan exposure in that branch.
SELECT
    b.branch_name,
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    SUM(l.loan_amount) AS customer_exposure
FROM loans l
JOIN customers c ON c.customer_id = l.customer_id
JOIN branches b ON b.branch_id = l.branch_id
GROUP BY b.branch_id, b.branch_name, c.customer_id, c.first_name, c.last_name
HAVING SUM(l.loan_amount) >
(
    SELECT AVG(customer_branch_exposure)
    FROM (
        SELECT SUM(l2.loan_amount) AS customer_branch_exposure
        FROM loans l2
        WHERE l2.branch_id = b.branch_id
        GROUP BY l2.customer_id
    ) AS branch_customer_exposures
);

-- Q6. UNCORRELATED SUBQUERY:
-- Same business test, using a derived table that calculates each
-- customer's exposure per branch, then compares it with branch average.
SELECT
    exposure.branch_id,
    b.branch_name,
    exposure.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name,
    exposure.total_exposure
FROM (
    SELECT branch_id, customer_id, SUM(loan_amount) AS total_exposure
    FROM loans
    GROUP BY branch_id, customer_id
) AS exposure
JOIN branches b ON b.branch_id = exposure.branch_id
JOIN customers c ON c.customer_id = exposure.customer_id
WHERE exposure.total_exposure >
(
    SELECT AVG(branch_exposure.total_exposure)
    FROM (
        SELECT branch_id, customer_id, SUM(loan_amount) AS total_exposure
        FROM loans
        GROUP BY branch_id, customer_id
    ) AS branch_exposure
    WHERE branch_exposure.branch_id = exposure.branch_id
);

-- Q7. EXISTS:
-- Find customers who have at least one loan and have no repayment
-- made in the last 60 days for any of their loans.
SELECT
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM loans l
    WHERE l.customer_id = c.customer_id
)
AND NOT EXISTS (
    SELECT 1
    FROM loans l
    JOIN repayments r ON r.loan_id = l.loan_id
    WHERE l.customer_id = c.customer_id
      AND r.paid_date >= DATE_SUB(CURDATE(), INTERVAL 60 DAY)
      AND r.paid_date <= CURDATE()
);

-- Q8. NOT EXISTS:
-- Same condition expressed with NOT EXISTS for customers with a
-- loan but without any repayment in the last 60 days.
SELECT
    c.customer_id,
    CONCAT(c.first_name, ' ', c.last_name) AS customer_name
FROM customers c
WHERE EXISTS (
    SELECT 1
    FROM loans l
    WHERE l.customer_id = c.customer_id
)
AND NOT EXISTS (
    SELECT 1
    FROM loans l
    INNER JOIN repayments r ON r.loan_id = l.loan_id
    WHERE l.customer_id = c.customer_id
      AND r.paid_date BETWEEN DATE_SUB(CURDATE(), INTERVAL 60 DAY) AND CURDATE()
);

-- Q9. NESTED SUBQUERY:
-- Find branches whose highest individual loan is greater than
-- the average of the maximum loan amount from every branch.
SELECT
    b.branch_id,
    b.branch_name,
    MAX(l.loan_amount) AS branch_max_loan
FROM branches b
JOIN loans l ON l.branch_id = b.branch_id
GROUP BY b.branch_id, b.branch_name
HAVING MAX(l.loan_amount) >
(
    SELECT AVG(branch_maximum)
    FROM (
        SELECT MAX(loan_amount) AS branch_maximum
        FROM loans
        GROUP BY branch_id
    ) AS branch_maxima
);

-- Q10. Show total loan count and total loan amount for each branch,
-- including branches with no loans.
SELECT
    b.branch_id,
    b.branch_name,
    COUNT(l.loan_id) AS total_loans,
    COALESCE(SUM(l.loan_amount), 0) AS total_loan_amount
FROM branches b
LEFT JOIN loans l ON l.branch_id = b.branch_id
GROUP BY b.branch_id, b.branch_name
ORDER BY b.branch_id;

-- ============================================================
-- 4. QUICK TABLE CHECKS
-- ============================================================
SELECT * FROM customers;
SELECT * FROM branches;
SELECT * FROM loans;
SELECT * FROM repayments;
SELECT * FROM loan_guarantors;
