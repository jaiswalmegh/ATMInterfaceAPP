# 💳 ATM Interface System

A **GUI-based ATM simulation** project developed in **Java** using **Swing** for the user interface, **JDBC** for database connectivity, and **MySQL** as the backend. The project follows the **MVC (Model-View-Controller)** design pattern for better structure and scalability.

---

## 📌 Project Overview

This system simulates basic ATM functionalities with an added **AI-powered chatbot assistant**. Key features include:

- 🔐 User Login (Card Number + PIN)
- 💰 Balance Inquiry
- 💸 Deposit Money
- 🏧 Withdraw Money
- 🔄 Real-time balance updates in the database
- 🤖 Integrated AI Chatbot Assistant to help users with common ATM operations

---

## 🧠 Chatbot Assistant Feature

A lightweight AI chatbot is available **after login**, providing:

- Smart replies to user queries (e.g., "How do I check my balance?")
- Guidance on using deposit/withdrawal features
- Friendly conversational assistance for a better user experience

### Sample Supported Questions

- *"Hi"*, *"Hello"*
- *"How to deposit money?"*
- *"Check balance"*
- *"Help"*, *"Assist me"*
- *"Withdraw cash"*
- *"Exit"*, *"Bye"*

---

## 🧰 Technologies Used

| Component       | Technology          |
|----------------|---------------------|
| Programming     | Java                |
| GUI Framework   | Java Swing          |
| Database        | MySQL               |
| DB Connectivity | JDBC                |
| IDE             | IntelliJ / Eclipse / NetBeans |
| Design Pattern  | MVC                 |

---

## 🗃️ Project Structure

```plaintext
ATMInterfaceSystem/
├── model/         # Java classes representing data models (e.g., User)
├── DAO/           # Data Access Objects (e.g., UserDAO.java)
├── ui/            # Swing GUI classes (ATMInterface.java)
├── resources/     # (Optional) Config files / assets
└── README.md      # This file
