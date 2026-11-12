# **MySQL System Database Exploration**  
## **Introduction**  
Apart from the database I created for this project, MySQL also provides built-in system databases.  
I explored these databases to understand how MySQL stores metadata, manages user privileges, and monitors server performance.  
The four system databases I explored were information_schema, mysql, performance_schema, and sys.  
## **1. information_schema**  
The information_schema database contains information about databases, tables, columns, constraints, and relationships.  
I used the following query to list the tables in my project database:  
SELECT TABLE_NAME  
FROM information_schema.TABLES  
WHERE TABLE_SCHEMA = 'retail_inventory';  
This helped me view the table names without opening each table individually.  
**Purpose:** It is useful for checking database structure and metadata.  
## **2. mysql**  
The mysql database stores important information related to MySQL user accounts, permissions, and access control.  
I used:  
SHOW TABLES FROM mysql;  
This displayed the available tables inside the MySQL system database.  
**Purpose:** It is used by MySQL to manage users, authentication, and privileges.  
I only explored the tables and did not modify the system database.  
## **3. performance_schema**  
The performance_schema database helps monitor MySQL server performance and database activity.  
I used:  
SHOW TABLES FROM performance_schema;  
This displayed the available performance monitoring tables.  
**Purpose:** It helps analyze server activity, SQL execution, connections, and performance-related information.  
## **4. sys**  
The sys database provides convenient views that make performance information easier to understand.  
I used:  
SHOW TABLES FROM sys;  
This displayed the available views and tables in the sys database.  
**Purpose:** It helps with performance analysis, troubleshooting, and monitoring MySQL.  
## **Summary**  
  
|  |  |  
| - | - |  
| **System database** |  **Main purpose** |  
| information_schema |  Database and table metadata |  
| mysql |  User accounts and privileges |  
| performance_schema |  Server performance monitoring |  
| sys |  Simplified performance and diagnostic views |  
  
## **Conclusion**  
Exploring these system databases helped me understand that MySQL maintains its own internal information to manage and monitor the server.  
I used read-only queries for this task and did not make changes to the MySQL system databases.  
   
