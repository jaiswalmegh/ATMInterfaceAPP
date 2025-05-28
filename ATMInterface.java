package ui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ATMInterface extends JFrame {
    private final UserDAO userDAO = new UserDAO();
    private User currentUser;
    private JLabel animatedLabel;
    private int dotCount = 0;

    public ATMInterface() {
        setTitle("Modern ATM Interface");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        showAnimatedWelcome();
    }

    private void showAnimatedWelcome() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(0, 51, 102));

        JLabel welcome = new JLabel("Welcome to Modern ATM", JLabel.CENTER);
        welcome.setForeground(Color.WHITE);
        welcome.setFont(new Font("Arial", Font.BOLD, 32));
        panel.add(welcome, BorderLayout.CENTER);

        animatedLabel = new JLabel("Loading", JLabel.CENTER);
        animatedLabel.setForeground(Color.LIGHT_GRAY);
        animatedLabel.setFont(new Font("Monospaced", Font.ITALIC, 20));
        panel.add(animatedLabel, BorderLayout.SOUTH);

        setContentPane(panel);
        revalidate(); repaint();

        Timer animationTimer = new Timer(500, (ActionEvent e) -> {
            dotCount = (dotCount + 1) % 4;
            animatedLabel.setText("Loading" + ".".repeat(dotCount));
        });
        animationTimer.start();

        Timer switchTimer = new Timer(3000, e -> {
            animationTimer.stop();
            showStartScreen();
        });
        switchTimer.setRepeats(false);
        switchTimer.start();
    }

    private void showStartScreen() {
        getContentPane().removeAll();
        JPanel startPanel = new JPanel(new BorderLayout());
        startPanel.setBackground(new Color(240, 248, 255));

        JLabel welcomeLabel = new JLabel("Select Transaction Type", JLabel.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 28));
        welcomeLabel.setForeground(new Color(0, 102, 204));
        startPanel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel btnPanel = new JPanel(new FlowLayout());
        JButton cardBtn = new JButton("Card Transaction");
        JButton cardlessBtn = new JButton("Cardless Transaction");

        cardBtn.addActionListener(e -> showCardLogin());
        cardlessBtn.addActionListener(e -> showCardlessLogin());

        btnPanel.add(cardBtn);
        btnPanel.add(cardlessBtn);
        startPanel.add(btnPanel, BorderLayout.CENTER);

        setContentPane(startPanel);
        revalidate(); repaint();
    }

    private void showCardLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Card Login"));
        panel.setBackground(new Color(230, 240, 255));

        JTextField cardField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        panel.add(new JLabel("Card Number:"));
        panel.add(cardField);
        panel.add(new JLabel("PIN:"));
        panel.add(pinField);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> showStartScreen());

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String card = cardField.getText();
            String pin = new String(pinField.getPassword());
            currentUser = userDAO.validateCardLogin(card, pin);
            if (currentUser != null) showWelcomeScreen();
            else JOptionPane.showMessageDialog(this, "Invalid credentials");
        });

        panel.add(backBtn);
        panel.add(loginBtn);
        setContentPane(panel);
        revalidate(); repaint();
    }

    private void showCardlessLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Cardless Login"));
        panel.setBackground(new Color(245, 255, 250));

        JTextField accField = new JTextField();
        JPasswordField pinField = new JPasswordField();

        panel.add(new JLabel("Account Number:"));
        panel.add(accField);
        panel.add(new JLabel("E-Banking PIN:"));
        panel.add(pinField);

        JButton backBtn = new JButton("Back");
        backBtn.addActionListener(e -> showStartScreen());

        JButton loginBtn = new JButton("Login");
        loginBtn.addActionListener(e -> {
            String acc = accField.getText();
            String pin = new String(pinField.getPassword());
            currentUser = userDAO.validateCardlessLogin(acc, pin);
            if (currentUser != null) showWelcomeScreen();
            else JOptionPane.showMessageDialog(this, "Invalid credentials");
        });

        panel.add(backBtn);
        panel.add(loginBtn);
        setContentPane(panel);
        revalidate(); repaint();
    }

    private void showWelcomeScreen() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(255, 250, 230));

        JLabel typingLabel = new JLabel("", JLabel.CENTER);
        typingLabel.setFont(new Font("Arial", Font.BOLD, 24));
        typingLabel.setForeground(new Color(0, 102, 51));

        JLabel accLabel = new JLabel("", JLabel.CENTER);
        accLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        accLabel.setForeground(new Color(102, 102, 102));

        panel.add(typingLabel, BorderLayout.CENTER);
        panel.add(accLabel, BorderLayout.SOUTH);

        setContentPane(panel);
        revalidate(); repaint();

        String welcomeText = "Welcome, " + currentUser.getName();
        final int[] index = {0};

        Timer typeTimer = new Timer(100, null);
        typeTimer.addActionListener(e -> {
            if (index[0] < welcomeText.length()) {
                typingLabel.setText(welcomeText.substring(0, index[0] + 1));
                index[0]++;
            } else {
                typeTimer.stop();
                accLabel.setText("Account Number: " + currentUser.getAccountNumber());

                // Delay before showing options
                Timer continueTimer = new Timer(1500, ev -> showATMOptions());
                continueTimer.setRepeats(false);
                continueTimer.start();
            }
        });

        typeTimer.start();
    }

    private void showATMOptions() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBackground(new Color(255, 255, 240));
        panel.setBorder(BorderFactory.createTitledBorder("ATM Options"));

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        JButton chatBtn = new JButton("Chat with our AI for help");
        JButton logoutBtn = new JButton("Logout");

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        balanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Current Balance: ₹" + currentUser.getBalance()));
        chatBtn.addActionListener(e -> showAIChatbot());
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showStartScreen();
        });

        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceBtn);
        panel.add(chatBtn);
        panel.add(logoutBtn);

        setContentPane(panel);
        revalidate(); repaint();
    }

    private void deposit() {
        String input = JOptionPane.showInputDialog(this, "Enter deposit amount:");
        try {
            double amt = Double.parseDouble(input);
            currentUser.setBalance(currentUser.getBalance() + amt);
            userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance());
            JOptionPane.showMessageDialog(this, "₹" + amt + " deposited successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void withdraw() {
        String input = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
        try {
            double amt = Double.parseDouble(input);
            if (amt > currentUser.getBalance()) {
                JOptionPane.showMessageDialog(this, "Insufficient balance.");
                return;
            }
            currentUser.setBalance(currentUser.getBalance() - amt);
            userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance());
            JOptionPane.showMessageDialog(this, "₹" + amt + " withdrawn successfully!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid amount.");
        }
    }

    private void showAIChatbot() {
        JFrame chatFrame = new JFrame("ATM AI Assistant");
        chatFrame.setSize(400, 300);
        chatFrame.setLocationRelativeTo(this);
        chatFrame.setLayout(new BorderLayout());

        JTextArea chatArea = new JTextArea();
        chatArea.setEditable(false);
        JTextField chatInput = new JTextField();

        chatFrame.add(new JScrollPane(chatArea), BorderLayout.CENTER);
        chatFrame.add(chatInput, BorderLayout.SOUTH);

        chatInput.addActionListener(e -> {
            String input = chatInput.getText();
            chatArea.append("You: " + input + "\n");
            chatArea.append("Bot: " + botReply(input) + "\n\n");
            chatInput.setText("");
        });

        chatFrame.setVisible(true);
    }

    private String botReply(String msg) {
        msg = msg.toLowerCase();

        if (msg.contains("balance")) return "Click on 'Check Balance' to see your balance.";
        if (msg.contains("deposit")) return "Use 'Deposit' button to add funds to your account.";
        if (msg.contains("withdraw")) return "Click on 'Withdraw' to take out money.";
        if (msg.contains("help")) return "I can guide you with login, deposit, withdraw, and balance checks!";
        if (msg.contains("hi") || msg.contains("hello")) return "Hello! How can I assist you today?";
        return "Sorry, I didn't understand. Try asking about deposit, withdraw, or balance.";
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface().setVisible(true));
    }
}
