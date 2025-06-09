# 💳 ATM Interface System

A GUI-based ATM simulation project developed in Java using Swing for the user interface, JDBC for database connectivity, and MySQL as the backend. The project follows the MVC (Model-View-Controller) design pattern to ensure a clean separation of concerns, scalability, and maintainability.

---

## 📌 Project Overview

This system simulates fundamental ATM functionalities enriched with a user-friendly AI-powered chatbot assistant. The application allows users to interact seamlessly with the ATM interface, providing a real-world banking experience with enhanced usability.

### Key Features:
- **User Authentication:** Secure login via Card Number and PIN or Account Number with e-banking PIN.
- **Balance Inquiry:** Users can check their current account balance instantly.
- **Deposit Money:** Allows users to deposit funds into their account.
- **Withdraw Money:** Enables users to withdraw cash with confirmation prompts.
- **Real-time Updates:** All transactions update the user’s balance immediately in the MySQL database.
- **AI Chatbot Assistant:** After login, users can interact with an integrated chatbot that provides helpful guidance on ATM functions, answers common questions, and improves user experience through conversational assistance.

---

## 🤖 AI Chatbot Assistant

The built-in chatbot is designed to assist users with typical ATM operations and general queries, making the system intuitive even for first-time users. It supports questions like:

- “How do I check my balance?”
- “How to deposit money?”
- “Withdraw cash”
- “Help” or “Assist me”
- Greetings such as “Hi”, “Hello”
- Commands like “Exit”, “Bye” to close the chatbot session

The chatbot replies with smart, contextual responses to guide users step-by-step through their banking tasks.

---

## 🧰 Technologies Used

| Component       | Technology               |
|-----------------|--------------------------|
| Programming     | Java                     |
| GUI Framework   | Java Swing               |
| Database        | MySQL                    |
| DB Connectivity | JDBC                     |
| IDE             | IntelliJ IDEA / Eclipse / NetBeans |
| Design Pattern  | Model-View-Controller (MVC) |

---

## 🗃️ Project Structure

YAML


---

## ⚙️ Setup and Usage

1. **Database Setup:** Create a MySQL database named `myatm_db` and a `users` table with fields for card number, PIN, account number, e-banking PIN, name, and balance.
2. **Configure DB Connection:** Update the database credentials in the `db/DBConnection.java` file.
3. **Run Application:** Compile and launch the `ui.ATMInterface` class. Use the GUI to perform login and ATM operations.
4. **Interact with Chatbot:** Post-login, engage with the chatbot for assistance on transactions and common queries.

---

## 🚀 Future Improvements

- Enhanced security features such as encryption and multi-factor authentication.
- Transaction history and mini statement generation.
- Support for PIN reset and account management.
- More advanced AI chatbot with natural language understanding.

---
🗄️🔥 **MySQL Database Schema & Setup** 🔥🗄️
-- Create and use the database
CREATE DATABASE IF NOT EXISTS myatm_db;
USE myatm_db;

-- Create the users table with all columns including 'name'
CREATE TABLE users (
    card_number VARCHAR(20) PRIMARY KEY,
    pin VARCHAR(10),
    account_number VARCHAR(20) UNIQUE,
    ebanking_pin VARCHAR(10),
    balance DOUBLE DEFAULT 0.0,
    name VARCHAR(100)
);

-- Insert user data including names
INSERT INTO users (card_number, pin, account_number, ebanking_pin, balance, name) VALUES
('1234567890', '1234', '9876543210', '4321', 5000.0, 'Anjali Sharma'),
('1111222233', '5678', '1020304050', '7788', 3000.0, 'Rohan Mehta'),
('9192939495', '0062', '1200564892', '6286', 9000.0, 'Shivam Mishra'),
('951954880853', '0210', '1980104000013280', '0210', 50000.0, 'Megh Jaiswal'),
('001002003004', '1111', '1122334455', '2222', 20000.0, 'Navneet Kumar Pandey');

-- Create the transactions table
CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    card_number VARCHAR(20),
    type VARCHAR(20),          -- 'Deposit' or 'Withdraw'
    amount DOUBLE,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
);



## 📄 License

This project is open-source and available under the MIT License.

---

Created by Megh Jaiswal
