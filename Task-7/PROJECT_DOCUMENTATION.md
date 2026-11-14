# Set 2 – Bank Loan Portfolio

## Database Structure

**Database:** `bank_loan_portfolio`

The assignment did not provide table definitions. The following schema and sample data were created from the business scenario.

### Tables

| Table | Purpose |
|---|---|
| `customers` | Stores borrower and guarantor customer details |
| `branches` | Stores bank branch details |
| `loans` | Stores loan amount, borrower, branch, rate, start date and tenure |
| `repayments` | Stores scheduled instalments and payment details |
| `loan_guarantors` | Links a loan to the customer who guarantees it |

### Relationships

- `loans.customer_id` references `customers.customer_id`.
- `loans.branch_id` references `branches.branch_id`.
- `repayments.loan_id` references `loans.loan_id`.
- `loan_guarantors.loan_id` references `loans.loan_id`.
- `loan_guarantors.guarantor_customer_id` references `customers.customer_id`.

A guarantor is represented as a customer. This makes it possible to check whether a guarantor is also a borrower by comparing guarantor customer IDs with borrower customer IDs.

---

## Task 1. Find loans overdue by more than three instalments

**Tables used:** `loans`, `customers`, `repayments`

**Query approach:** `INNER JOIN`, `WHERE`, `GROUP BY`, and `HAVING`.

**Why these joins and clauses:** The loan table identifies the borrower and the repayment schedule. INNER JOIN returns loans that have matching customers and repayment rows. The WHERE clause filters unpaid instalments whose due dates have passed. GROUP BY counts overdue instalments per loan, and HAVING keeps only loans with more than three overdue instalments.

**Overdue definition used:** An instalment is overdue when `due_date < CURDATE()` and `paid_date IS NULL`.

---

## Task 2. Find customers who have loans in more than one branch

**Tables used:** `customers`, `loans`

**Query approach:** `INNER JOIN`, `GROUP BY`, `COUNT(DISTINCT ...)`, and `HAVING`.

**Why this join:** INNER JOIN connects each borrower to their loan records. `COUNT(DISTINCT branch_id)` counts different branches rather than counting multiple loans from the same branch. HAVING filters customers whose branch count is greater than one.

---

## Task 3. Find guarantors who are also borrowers

**Tables used:** `loan_guarantors`, `customers`, `loans`

**Query approach:** INNER JOIN, including a self-relationship through the customer entity.

**Why these joins:** `loan_guarantors` identifies the customer acting as guarantor. The join to `customers` retrieves that person's details. Joining that customer to `loans` checks whether the same customer ID also appears as a borrower. `DISTINCT` avoids repeating a guarantor who guarantees or borrows through multiple loans.

---

## Task 4. Find branches that do not have any loans

**Tables used:** `branches`, `loans`

**Query approach:** `LEFT JOIN` with `IS NULL`.

**Why this join:** LEFT JOIN keeps every branch, including branches without matching loan records. For a branch with no loan, the loan columns are NULL. Filtering with `WHERE loan_id IS NULL` returns only those branches.

---

## Task 5. Find customers whose total loan exposure is greater than the branch average – correlated subquery

**Tables used:** `loans`, `customers`, `branches`

**Query approach:** JOIN, GROUP BY, HAVING, and a correlated subquery.

**Why this approach:** The outer query calculates each customer's total loan amount in a branch. The subquery calculates the average total exposure per customer for the branch currently being evaluated. It is correlated because it refers to the outer query's `branch_id`. HAVING compares the customer's total exposure with that branch average.

---

## Task 6. Find customers whose total loan exposure is greater than the branch average – uncorrelated/derived-table approach

**Tables used:** `loans`, `customers`, `branches`

**Query approach:** Derived tables and a scalar subquery.

**Why this approach:** The derived table first calculates each customer's total exposure per branch. The comparison subquery calculates the average of those customer exposures for the relevant branch. The derived table makes the grouped exposure values available for comparison without repeating the aggregation in the outer query.

---

## Task 7. Find customers who have a loan but no repayment in the last 60 days – EXISTS

**Tables used:** `customers`, `loans`, `repayments`

**Query approach:** `EXISTS` and `NOT EXISTS`.

**Why these operators:** EXISTS checks that the customer has at least one loan. NOT EXISTS checks that no repayment matching that customer's loans has a paid date within the last 60 days. These operators test whether matching records exist without needing to return repayment rows.

---

## Task 8. Find customers who have a loan but no repayment in the last 60 days – NOT EXISTS

**Tables used:** `customers`, `loans`, `repayments`

**Query approach:** EXISTS and NOT EXISTS, using an INNER JOIN inside the subquery.

**Why these operators:** EXISTS confirms that the customer has a loan. The NOT EXISTS subquery joins loans to repayments and checks that no repayment was made during the last 60 days. This is an alternative query formulation of the same business condition in Task 7.

---

## Task 9. Find branches whose highest loan is greater than the average of all branch maximums

**Tables used:** `branches`, `loans`

**Query approach:** Nested subquery, MAX, AVG, GROUP BY, and HAVING.

**Why this approach:** The inner query finds the maximum loan amount for each branch. The next query calculates the average of those branch maximums. The outer query calculates each branch's maximum loan and uses HAVING to return branches whose maximum is greater than that average.

---

## Task 10. Show the number and total amount of loans for every branch

**Tables used:** `branches`, `loans`

**Query approach:** `LEFT JOIN`, `COUNT`, `SUM`, `COALESCE`, and `GROUP BY`.

**Why this join:** LEFT JOIN includes branches even if they have no loans. COUNT counts matching loan IDs, while COALESCE displays zero instead of NULL for the total loan amount of a branch without loans.

---

## SQL File

The queries for all ten tasks, table creation statements, and sample data are in [`advanced_reports.sql`](advanced_reports.sql).
