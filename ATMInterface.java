package ui;

import DAO.UserDAO;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Timer;
import java.util.TimerTask;

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
        panel.setBackground(new Color(0, 0, 51));

        JLabel welcome = new JLabel("Welcome to Modern ATM", JLabel.CENTER);
        welcome.setForeground(Color.CYAN);
        welcome.setFont(new Font("Verdana", Font.BOLD, 30));
        welcome.setBorder(BorderFactory.createEmptyBorder(100, 0, 20, 0));

        animatedLabel = new JLabel("Loading", JLabel.CENTER);
        animatedLabel.setForeground(Color.LIGHT_GRAY);
        animatedLabel.setFont(new Font("Monospaced", Font.PLAIN, 20));

        panel.add(welcome, BorderLayout.CENTER);
        panel.add(animatedLabel, BorderLayout.SOUTH);

        setContentPane(panel);
        revalidate(); repaint();

        // Use java.util.Timer for animation
        Timer animationTimer = new Timer();
        animationTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    dotCount = (dotCount + 1) % 4;
                    animatedLabel.setText("Loading" + ".".repeat(dotCount));
                });
            }
        }, 0, 500);

        // Use javax.swing.Timer to stop animation and proceed (no repeat!)
        javax.swing.Timer switchTimer = new javax.swing.Timer(3000, e -> {
            animationTimer.cancel();  // stop the dot animation
            showStartScreen();        // go to Card/Cardless screen
        });
        switchTimer.setRepeats(false);
        switchTimer.start();
    }
    private void showStartScreen() {
        getContentPane().removeAll();
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(230, 245, 255));

        JLabel label = new JLabel("Select Transaction Type", JLabel.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 26));
        label.setForeground(new Color(0, 102, 204));
        panel.add(label, BorderLayout.NORTH);

        JPanel buttons = new JPanel(new FlowLayout());
        JButton card = new JButton("Card Transaction");
        JButton cardless = new JButton("Cardless Transaction");
        buttons.add(card);
        buttons.add(cardless);

        card.addActionListener(e -> showCardLogin());
        cardless.addActionListener(e -> showCardlessLogin());

        panel.add(buttons, BorderLayout.CENTER);
        setContentPane(panel);
        revalidate(); repaint();
    }

    private void showCardLogin() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBackground(new Color(255, 240, 245));
        panel.setBorder(BorderFactory.createTitledBorder("Card Login"));

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
            currentUser = userDAO.validateCardLogin(cardField.getText(), new String(pinField.getPassword()));
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
        panel.setBackground(new Color(255, 255, 224));
        panel.setBorder(BorderFactory.createTitledBorder("Cardless Login"));

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
            currentUser = userDAO.validateCardlessLogin(accField.getText(), new String(pinField.getPassword()));
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
        panel.setBackground(new Color(204, 255, 229));

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

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    if (index[0] < welcomeText.length()) {
                        typingLabel.setText(welcomeText.substring(0, index[0] + 1));
                        index[0]++;
                    } else {
                        timer.cancel();
                        accLabel.setText("Account Number: " + currentUser.getAccountNumber());

                        // Proper one-time delay before ATM menu
                        javax.swing.Timer nextScreenTimer = new javax.swing.Timer(1500, ev -> showATMOptions());
                        nextScreenTimer.setRepeats(false);
                        nextScreenTimer.start();
                    }
                });
            }
        }, 0, 100);
    }
    private void showATMOptions() {
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(new Color(240, 255, 255));
        panel.setBorder(BorderFactory.createTitledBorder("ATM Options"));

        JButton depositBtn = new JButton("Deposit");
        JButton withdrawBtn = new JButton("Withdraw");
        JButton balanceBtn = new JButton("Check Balance");
        JButton chatBtn = new JButton("Chat with our AI for help");
        JButton logoutBtn = new JButton("Logout");
        JButton quitBtn = new JButton("Quit");

        depositBtn.addActionListener(e -> deposit());
        withdrawBtn.addActionListener(e -> withdraw());
        balanceBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Current Balance: ₹" + currentUser.getBalance()));
        chatBtn.addActionListener(e -> showAIChatbot());
        logoutBtn.addActionListener(e -> {
            currentUser = null;
            showStartScreen();
        });
        quitBtn.addActionListener(e -> showExitAnimation());

        panel.add(depositBtn);
        panel.add(withdrawBtn);
        panel.add(balanceBtn);
        panel.add(chatBtn);
        panel.add(logoutBtn);
        panel.add(quitBtn);

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
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to withdraw ₹" + amt + "?", "Confirm Withdrawal", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (amt > currentUser.getBalance()) {
                    JOptionPane.showMessageDialog(this, "Insufficient balance.");
                } else {
                    currentUser.setBalance(currentUser.getBalance() - amt);
                    userDAO.updateBalance(currentUser.getCardNumber(), currentUser.getBalance());
                    JOptionPane.showMessageDialog(this, "₹" + amt + " withdrawn successfully!");
                }
            }
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
        if (msg.contains("balance")) return "Click 'Check Balance' to view balance.";
        if (msg.contains("deposit")) return "Click 'Deposit' to add money.";
        if (msg.contains("withdraw")) return "Use 'Withdraw' to take out money.";
        if (msg.contains("hi") || msg.contains("hello")) return "Hello! How can I assist you today?";
        return "Try asking about balance, deposit, or withdraw.";
    }

    private void showExitAnimation() {
        getContentPane().removeAll();
        JLabel thankYouLabel = new JLabel("", JLabel.CENTER);
        thankYouLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        thankYouLabel.setForeground(new Color(0, 128, 128));
        setLayout(new BorderLayout());
        add(thankYouLabel, BorderLayout.CENTER);
        revalidate(); repaint();

        String message = "Thank you for using our ATM. Hope you liked the service.";
        final int[] index = {0};

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (index[0] < message.length()) {
                    thankYouLabel.setText(thankYouLabel.getText() + message.charAt(index[0]));
                    index[0]++;
                } else {
                    timer.cancel();
                }
            }
        }, 0, 80);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new ATMInterface().setVisible(true));
    }
}
