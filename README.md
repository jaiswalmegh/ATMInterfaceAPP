# ATM Interface System

A GUI-based ATM simulation project developed in Java using Swing for the user interface, JDBC for database connectivity, and MySQL as the backend. This project follows the MVC (Model-View-Controller) architecture to keep the logic clean and scalable.

## 📌 Project Overview

This system mimics basic ATM functionalities including:

* 🔐 User Login (Card Number + PIN)
* 💰 Balance Inquiry
* 💸 Deposit Money
* 🏧 Withdraw Money
* 🔄 Real-time balance updates
* 🤖 AI-assisted Chatbot integrated for user guidance and assistance within the ATM interface

## 🧰 Technologies Used

| Component      | Technology                    |
| -------------- | ----------------------------- |
| Programming    | Java                          |
| GUI            | Java Swing                    |
| Database       | MySQL                         |
| DB Connection  | JDBC                          |
| IDE            | IntelliJ / Eclipse / NetBeans |
| Design Pattern | MVC                           |

## 🗃️ Project Structure

* **model:** Contains data models (e.g., User)
* **DAO:** Data Access Objects to interact with the database (e.g., UserDAO)
* **ui:** GUI components including the main ATM interface with chatbot integration
* **controller:** Logic controllers for handling user interactions (if applicable)

## 🛢️ Database Schema

Database Name: `atm_db`

Table Name: `users`

```sql
CREATE DATABASE atm_db;

CREATE TABLE users (
    card_number VARCHAR(20) PRIMARY KEY,
    pin VARCHAR(10),
    balance DOUBLE
);

INSERT INTO users VALUES ('1234567890', '1234', 5000.0);
```

## 🆕 New Feature: AI-assisted Chatbot

An AI-powered chatbot has been integrated directly into the ATM interface, available after user login on the right panel. It helps users by:

* Answering FAQs about balance, deposit, withdrawal, and login procedures
* Providing real-time assistance within the app
* Offering friendly conversational responses to guide users through ATM operations

### Chatbot Features

* Text input for user queries
* Instant chatbot replies within the interface
* Simple natural language processing for common ATM-related questions
* Enhances user experience with interactive help

## 🚀 How to Run

1. Set up the `atm_db` MySQL database using the provided schema.
2. Import the project into your preferred Java IDE.
3. Ensure the JDBC driver for MySQL is included in the project libraries.
4. Run the `ATMInterface` class.
5. Use the following test credentials to login:

```
Card Number: 1234567890
PIN: 1234
```

6. Interact with ATM options and use the chatbot for assistance.

