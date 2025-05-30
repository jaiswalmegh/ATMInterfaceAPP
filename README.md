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

## 📄 License

This project is open-source and available under the MIT License.

---

Created by Megh Jaiswal
