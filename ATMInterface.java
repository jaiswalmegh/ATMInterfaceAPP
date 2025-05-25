package ui;

import dao.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ATMInterface extends JFrame {
    private JTextField cardField;
    private JPasswordField pinField;
    private JLabel balanceLabel;
    private User currentUser;
    private final UserDAO userDAO = new UserDAO();

    public ATMInterface() {
        setTitle("ATM Interface");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        loginPanel.add(new JLabel("Card Number:"));
        cardField = new JTextField();
        loginPanel.add(cardField);

        loginPanel.add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        loginPanel.add(pinField);

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> login());
        loginPanel.add(loginBtn);

        add(loginPanel, BorderLayout.NORTH);
    }

    private void login() {
        String cardNumber = cardField.getText();
        String pin = new String(pinField.getPassword());

        currentUser = userDAO.validateUser(cardNumber, pin);
        if (currentUser != null) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials");
        }
    }

    private void showMainMenu() {
        getContentPane().removeAll();
        setLayout(new GridLayout(5, 1, 10, 10));

        balanceLabel = new JLabel("Balance: $" + currentUser.getBalance(), SwingConstants.CENTER);
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        logoutBtn.addActionListener(e -> {
            getContentPane().removeAll();
            currentUser = null;
            new ATMInterface().setVisible(true);
            dispose();
        });

        add(balanceLabel);
        add(depositBtn);
        add(withdrawBtn);
        add(logoutBtn);

        revalidate();
        repaint();
    }

    private void deposit() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        try {
            double amount = Double.parseDouble(amountStr);
            currentUser.setBalance(currentUser.getBalance() + amount);
            if (userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance())) {
                balanceLabel.setText("Balance: $" + currentUser.getBalance());
            }
        } catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void withdraw() {
        String amountStr = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        try {
            double amount = Double.parseDouble(amountStr);
            if (amount <= currentUser.getBalance()) {
                currentUser.setBalance(currentUser.getBalance() - amount);
                if (userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance())) {
                    balanceLabel.setText("Balance: $" + currentUser.getBalance());
                }
            } else {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
            }
        } catch (NumberFormatException ignored) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }
}