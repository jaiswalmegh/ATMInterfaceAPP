# 💳 ATM Interface System

A GUI-based ATM simulation project developed in Java using **Swing** for the user interface, **JDBC** for database connectivity, and **MySQL** as the backend. This project follows the **MVC (Model-View-Controller)** architecture to keep the logic clean and scalable.

---

## 📌 Project Overview

This system mimics basic ATM functionalities like:

- 🔐 User Login (Card Number + PIN)
- 💰 Balance Inquiry
- 💸 Deposit Money
- 🏧 Withdraw Money
- 🔄 Real-time balance updates

---

## 🧰 Technologies Used

| Component         | Technology        |
|------------------|-------------------|
| Programming       | Java              |
| GUI               | Java Swing        |
| Database          | MySQL             |
| DB Connection     | JDBC              |
| IDE               | IntelliJ / Eclipse / NetBeans |
| Design Pattern    | MVC               |

---

## 🗃️ Project Structure


---

## 🛢️ Database Schema

**Database Name**: `atm_db`  
**Table Name**: `users`

```sql
CREATE DATABASE atm_db;

CREATE TABLE users (
    card_number VARCHAR(20) PRIMARY KEY,
    pin VARCHAR(10),
    balance DOUBLE
);

INSERT INTO users VALUES ('1234567890', '1234', 5000.0);
