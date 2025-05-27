package ui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ATMInterface extends JFrame {
    private JTextField cardField;
    private JPasswordField pinField;
    private JLabel balanceLabel;

    private JTextArea chatArea;
    private JTextField chatInput;
    private JButton sendBtn;

    private JPanel chatbotPanel; // only added after login

    private User currentUser;
    private final UserDAO userDAO = new UserDAO();

    public ATMInterface() {
        setTitle("ATM Interface System");
        setSize(800, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        showLoginUI();
    }

    private void showLoginUI() {
        JPanel loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        loginPanel.setBorder(BorderFactory.createTitledBorder("Login"));

        loginPanel.add(new JLabel("Card Number:"));
        cardField = new JTextField();
        loginPanel.add(cardField);

        loginPanel.add(new JLabel("PIN:"));
        pinField = new JPasswordField();
        loginPanel.add(pinField);

        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> login());
        loginPanel.add(new JLabel());
        loginPanel.add(loginButton);

        getContentPane().removeAll();
        add(loginPanel, BorderLayout.CENTER);
        revalidate();
        repaint();
    }

    private void login() {
        String card = cardField.getText().trim();
        String pin = new String(pinField.getPassword()).trim();
        currentUser = userDAO.validateUser(card, pin);

        if (currentUser != null) {
            showMainMenu();
        } else {
            JOptionPane.showMessageDialog(this, "Invalid credentials!", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showMainMenu() {
        // Left side: ATM actions
        JPanel menuPanel = new JPanel(new GridLayout(5, 1, 10, 10));
        menuPanel.setBorder(BorderFactory.createTitledBorder("ATM Options"));

        balanceLabel = new JLabel("Balance: $" + currentUser.getBalance(), JLabel.CENTER);
        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        logoutBtn.addActionListener(e -> {
            dispose();
            new ATMInterface().setVisible(true);
        });

        menuPanel.add(balanceLabel);
        menuPanel.add(depositBtn);
        menuPanel.add(withdrawBtn);
        menuPanel.add(logoutBtn);

        getContentPane().removeAll();
        add(menuPanel, BorderLayout.CENTER);

        // Right side: Chatbot panel
        chatbotPanel = createChatPanel();
        add(chatbotPanel, BorderLayout.EAST);

        revalidate();
        repaint();
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to deposit:");
        try {
            double amt = Double.parseDouble(input);
            currentUser.setBalance(currentUser.getBalance() + amt);
            if (userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance())) {
                balanceLabel.setText("Balance: $" + currentUser.getBalance());
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog(this, "Enter amount to withdraw:");
        try {
            double amt = Double.parseDouble(input);
            if (amt > currentUser.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient funds.");
            } else {
                currentUser.setBalance(currentUser.getBalance() - amt);
                if (userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance())) {
                    balanceLabel.setText("Balance: $" + currentUser.getBalance());
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private JPanel createChatPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(300, 0));
        panel.setBorder(BorderFactory.createTitledBorder("ATM AI Assistant"));

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        panel.add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatInput = new JTextField();
        sendBtn = new JButton("Send");
        sendBtn.addActionListener(e -> sendMessage());
        chatInput.addActionListener(e -> sendMessage());

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(chatInput, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);

        panel.add(inputPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void sendMessage() {
        String userMsg = chatInput.getText().trim();
        if (!userMsg.isEmpty()) {
            chatArea.append("You: " + userMsg + "\n");
            chatInput.setText("");
            chatArea.append("Bot: " + botReply(userMsg) + "\n\n");
        }
    }

    private String botReply(String msg) {
        msg = msg.toLowerCase();

        if (msg.contains("hi") || msg.contains("hello")) {
            return "Hello! I’m your ATM Assistant. Ask me about balance, withdrawal, deposit, or anything else!";
        }

        if (msg.contains("balance") || msg.contains("check balance")) {
            return currentUser != null
                    ? "Your current balance is $" + currentUser.getBalance()
                    : "Please log in to check your balance.";
        }

        if (msg.contains("withdraw")) {
            return currentUser != null
                    ? "To withdraw money, click the 'Withdraw' button and enter the amount."
                    : "Please log in to use the withdrawal feature.";
        }

        if (msg.contains("deposit")) {
            return currentUser != null
                    ? "To deposit money, click the 'Deposit' button and enter the amount."
                    : "Please log in to use the deposit feature.";
        }

        if (msg.contains("login") || msg.contains("how to login")) {
            return "Enter your card number and PIN, then click the Login button.";
        }

        if (msg.contains("help") || msg.contains("assist")) {
            return "Sure! I can help with:\n- Logging in\n- Checking balance\n- Deposits & withdrawals\nJust type your question.";
        }

        if (msg.contains("exit") || msg.contains("bye")) {
            return "Goodbye! Have a great day using ATM Assistant 😊";
        }

        return "Sorry, I didn't quite catch that. Try asking about balance, withdraw, deposit, or help.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface().setVisible(true));
    }
}
